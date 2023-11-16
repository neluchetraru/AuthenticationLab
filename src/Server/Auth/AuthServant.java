package Server.Auth;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.Scanner;

public class AuthServant  implements AuthService {

    private final Connection connection;
    private final SessionManager sm;
    public AuthServant(Connection connection, SessionManager sm) throws RemoteException {
        super();
        this.sm = sm;
        this.connection = connection;
    }

    public String echo(String input) throws RemoteException {
        return "From server " + input;
    }

    @Override
    public String login(String username, String password) throws RemoteException {

        String sessionID = null;

        String sql = "SELECT * FROM UsersP2 WHERE UserName = ?";


        ResultSet resultLogin = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            resultLogin = preparedStatement.executeQuery();

            while (resultLogin.next()) {
                String userNameDB = resultLogin.getString("userName");
                String userPasswordDB = resultLogin.getString("userPassword");
                if (username.equals(userNameDB)){
                    if (Encryption.hashPassword(password).equals(userPasswordDB)){
                        System.out.println("Successfully logged in");
                        sessionID = this.sm.addSession(username);
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

        String sql = "INSERT INTO UsersP2 (UserName, UserPassword, RoleID) VALUES (?, ?, 3)"; // By default the role is 4 which is a user
        String hashedPassword = Encryption.hashPassword(password);
        System.out.println(hashedPassword);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            int result = preparedStatement.executeUpdate();
            return this.sm.addSession(username);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RemoteException("Something went wrong");
        }
    }

    @Override
    public boolean checkRole(String sessionID) throws RemoteException{
        String sql = "SELECT * FROM UsersP2 NATURAL JOIN Roles WHERE UserName = ?;";
        String userName = this.sm.getUser(sessionID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String role = result.getString("RoleName");
                if (role.equals("admin")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }

        return false;
    }

    @Override
    public void changeRole(String sessionID, String userName, String role) {
        String sql = "UPDATE UsersP2 SET RoleID = (SELECT RoleID FROM Roles WHERE RoleName = ?) WHERE UserName = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }


    }

    @Override
    public String getUserName(String sessionID) throws RemoteException {
        return this.sm.getUser(sessionID);
    }

    @Override
    public String getPermissions(String userName) {
        String sql = "SELECT * FROM UsersP2 NATURAL JOIN Roles NATURAL JOIN Permissions WHERE UserName = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet result = preparedStatement.executeQuery();
            StringBuilder permissions = new StringBuilder();
            while (result.next()) {
                String permission = result.getString("Permission");
                permissions.append(permission);
                if (!result.isLast())
                    permissions.append(", ");
            }

            return permissions.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }
    }

    @Override
    public String getRole(String userName) {
        String sql = "SELECT * FROM UsersP2 NATURAL JOIN Roles WHERE UserName = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getString("RoleName");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }

    }

    private boolean checkUserNameExists(String username) throws RemoteException {
        String sqlQuery = "SELECT 1 FROM UsersP2 WHERE UserName = ?";

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
