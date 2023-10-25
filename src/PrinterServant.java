import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {

    public PrinterServant() throws RemoteException {
        super();
    }
    public String echo(String input) throws RemoteException{
        return "From server " + input;
    }
}
