package Server.Auth;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager{
    private final ConcurrentHashMap<String, Session> sessionHashMap = new ConcurrentHashMap<>();

    public String addSession(String username)
    {
        Session session = new Session(username);
        String sessionID = Session.generateSessionID();

        sessionHashMap.put(sessionID, session);

        return sessionID;
    }

    public boolean checkSession(String sessionID) throws RemoteException {
        if(sessionHashMap.containsKey(sessionID))
        {
            Session session = sessionHashMap.get(sessionID);
            // Check if session is expired
            if(session.getExpirationDate().getTime() > System.currentTimeMillis())
            {
                return true;
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
}