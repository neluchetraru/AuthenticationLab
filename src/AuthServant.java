import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServant extends UnicastRemoteObject implements AuthService {

    public AuthServant() throws RemoteException {
        super();
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
    public String signup(String username, String password) throws RemoteException {
        return null;
    }
}
