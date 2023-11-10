package Server.Auth;


import java.rmi.RemoteException;
import java.sql.*;

public class AuthServant  implements AuthService {

    protected static Connection connection;
    private final SessionManager sm;
    public AuthServant(Connection connection, SessionManager sm) throws RemoteException {
        super();
        this.sm = sm;
        AuthServant.connection = connection;
    }

    public String echo(String input) throws RemoteException {
        return "From server " + input;
    }

    @Override
    public String login(String username, String password) throws RemoteException {

        String sessionID = null;

        String sql = "SELECT * FROM Users";

        ResultSet resultLogin;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            resultLogin = preparedStatement.executeQuery();
            while (resultLogin.next()) {
                String userNameDB = resultLogin.getString("userName");
                String userPasswordDB = resultLogin.getString("userPassword");
                int userIDDB = resultLogin.getInt("UserID");
                if (username.equals(userNameDB)){
                    if (Encryption.hashPassword(password).equals(userPasswordDB)){
                        System.out.println("Successfully logged in");
                        sessionID = this.sm.addSession(username, userIDDB);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RemoteException("Something went wrong");
        }
        if (sessionID == null) {
            throw new RemoteException("Invalid credentials.");
        }
        return sessionID;
    }

    @Override
    public void logout(String sessionID) throws RemoteException {
       this.sm.revokeSession(sessionID);
    }

    @Override
    public String signup(String username, String password) throws RemoteException {

        if(username == null || password == null) {
            throw new RemoteException("Username or password is null");

        } else if (username.isBlank() || username.isEmpty()) {
            throw new RemoteException("Username is empty");
        }
        else if (password.isBlank() || password.isEmpty()) {
            throw new RemoteException("Password is empty");
        }
        else if (password.length() < 4) {
            throw new RemoteException("Password does not meet the security requirements");
        }

        //Check if username already exists
        if(checkUserNameExists(username)) {
            throw new RemoteException("Username already exists");
        }

        String sql = "INSERT INTO Users (UserName, UserPassword) VALUES (?, ?)";
            String hashedPassword = Encryption.hashPassword(password);
            System.out.println(hashedPassword);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next()) {
                    int userID = resultSet.getInt(1);
                    return this.sm.addSession(username, userID);
                }
                else {
                    throw new RemoteException("Something went wrong");
                }


            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RemoteException("Something went wrong");
            }
    }

    private boolean checkUserNameExists(String username) throws RemoteException {
        String sqlQuery = "SELECT 1 FROM Users WHERE UserName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, username);

            return preparedStatement.executeQuery().next();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
