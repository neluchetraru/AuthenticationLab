package Server.Auth;

import Server.Auth.AuthService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthServant extends UnicastRemoteObject implements AuthService {

    private Connection connection;

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
}
