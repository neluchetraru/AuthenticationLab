import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrinterService extends Remote {
    public String echo(String input) throws RemoteException;
}
