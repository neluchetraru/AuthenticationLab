package Server.Auth;

import java.rmi.*;

public interface AuthService extends Remote {

    public String login(String username, String password) throws RemoteException;
    public void logout(String token) throws RemoteException;
    public String signup(String username, String password) throws RemoteException;

}
