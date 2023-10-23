import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServant extends UnicastRemoteObject implements HelloService {

    public HelloServant() throws RemoteException {
        super();
    }
    public String echo(String input) throws RemoteException{
        return "From server " + input;
    }
}
