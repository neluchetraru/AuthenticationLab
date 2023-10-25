import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {

    public PrinterServant() throws RemoteException {
        super();
    }
    public String echo(String input) throws RemoteException{
        return "From server " + input;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        return null;
    }

    @Override
    public String queue(String printer) throws RemoteException {
        return null;
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        return null;
    }

    @Override
    public String start() throws RemoteException {
        return null;
    }

    @Override
    public String stop() throws RemoteException {
        return null;
    }

    @Override
    public String restart() throws RemoteException {
        return null;
    }

    @Override
    public String status(String printer) throws RemoteException {
        return null;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return null;
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        return null;
    }
}
