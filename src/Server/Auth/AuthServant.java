package Server.Auth;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthServant extends UnicastRemoteObject implements AuthService {

    private final Connection connection;

    public AuthServant(Connection connection) throws RemoteException {
        super();
        this.connection = connection;
    }

    public String echo(String input) throws RemoteException{
        return "From server " + input;
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        return null;
    }

    @Override
    public String logout(String token) throws RemoteException {
        return null;
    }

    @Override
    public boolean signup(String username, String password) throws RemoteException {

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

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                int result = preparedStatement.executeUpdate();
                return result > 0;
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
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
