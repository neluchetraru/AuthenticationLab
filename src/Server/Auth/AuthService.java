package Server.Auth;

import java.rmi.*;

public interface AuthService extends Remote {

    public String login(String username, String password) throws RemoteException;
    public void logout(String token) throws RemoteException;
    public String signup(String username, String password) throws RemoteException;

    public boolean checkRole(String sessionID) throws RemoteException;
    public void changeRole(String sessionID, String userName, String role) throws RemoteException;

    public String getUserName(String sessionID) throws RemoteException;

    public String getPermissions(String userName) throws RemoteException;

    public String getRole(String sessionID) throws RemoteException;
}
