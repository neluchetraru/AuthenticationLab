package Server.Printer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrinterService extends Remote {
    public void echo(String input) throws RemoteException;
    public void print(String SessionID, String filename, String printer) throws RemoteException;
    public void queue(String SessionID, String printer) throws RemoteException;
    public void topQueue(String SessionID, String printer, int job) throws RemoteException;
    public void start(String SessionID) throws RemoteException;
    public void stop(String SessionID) throws RemoteException;
    public void restart(String SessionID) throws RemoteException;
    public void status(String SessionID, String printer) throws RemoteException;
    public void readConfig(String SessionID, String parameter) throws RemoteException;
    public void setConfig(String SessionID, String parameter, String value) throws RemoteException;
}
