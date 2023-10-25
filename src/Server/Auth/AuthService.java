package Server.Auth;

import java.rmi.*;

public interface AuthService extends Remote {

    public String login(String username, String password) throws RemoteException;
    public String logout(String token) throws RemoteException;
    public boolean signup(String username, String password) throws RemoteException;

}
