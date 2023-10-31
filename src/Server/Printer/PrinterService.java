package Server.Printer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrinterService extends Remote {
    public void echo(String input) throws RemoteException;
    public void print(String filename, String printer) throws RemoteException;
    public void queue(String printer) throws RemoteException;
    public void topQueue(String printer, int job) throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;
    public void restart() throws RemoteException;
    public void status(String printer) throws RemoteException;
    public void readConfig(String parameter) throws RemoteException;
    public void setConfig(String parameter, String value) throws RemoteException;
}
