package Server.Auth;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager{
    private final ConcurrentHashMap<String, Session> sessionHashMap = new ConcurrentHashMap<>();

    public String addSession(String username, int userID)
    {
        Session session = new Session(username, userID);
        String sessionID = Session.generateSessionID();

        sessionHashMap.put(sessionID, session);

        return sessionID;
    }
    public String getUser(String sessionID) throws RemoteException {
        String user = sessionHashMap.get(sessionID).getUsername();

        if (user == null) {
            throw new RemoteException("Invalid credentials.");
        }
        return user;
    }

    public boolean checkSession(String sessionID, String callingMethodName) throws RemoteException {


        if(sessionHashMap.containsKey(sessionID))
        {
            Session session = sessionHashMap.get(sessionID);
            // Check if session is expired
            if(session.getExpirationDate().isAfter(LocalDateTime.now()))
            {
                if(checkFunctionPersmission(session.getUserID(), callingMethodName))
                {
                    return true;
                }
                else {
                    throw new RemoteException("You do not have permission to run this command");
                }
            }
            else
            {
                sessionHashMap.remove(sessionID);
                throw new RemoteException("Session expired");
            }
        }
        else
        {
            throw new RemoteException("Session expired");
        }
    }

    public void revokeSession(String sessionID)
    {
        sessionHashMap.remove(sessionID);
    }


    private boolean checkFunctionPersmission(int userID, String callingMethodName) throws RemoteException {

        String sql = "Select * from User_Functions where UserID = ? and AllowedFunctions = ?";

        try (PreparedStatement preparedStatement = AuthServant.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, callingMethodName);
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                return false;
            }
            else{
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RemoteException("Something went wrong");
        }
    }
}