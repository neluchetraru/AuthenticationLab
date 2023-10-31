package Server.Printer;

import Server.Auth.SessionManager;
import Server.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {
    private final Logger logger;
    SessionManager sm = null;
    public PrinterServant(Logger logger, SessionManager sm) throws RemoteException {
        super();
        this.logger = logger;
        this.sm = sm;
    }
    public void echo(String input) throws RemoteException{
        this.logger.log(input);
    }

    @Override
    public void print(String SessionID, String filename, String printer) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Printing " + filename + " on " + printer);
    }

    @Override
    public void queue(String SessionID, String printer) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Queueing " + printer);
    }

    @Override
    public void topQueue(String SessionID, String printer, int job) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Topping " + printer + " with job " + job);
    }

    @Override
    public void start(String SessionID)  throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Starting the print server");
    }

    @Override
    public void stop(String SessionID) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Stopping the print server");
    }

    @Override
    public void restart(String SessionID) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Restarting the print server");
    }

    @Override
    public void status(String SessionID, String printer) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Status of " + printer);
    }

    @Override
    public void readConfig(String SessionID, String parameter) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Reading config of " + parameter);
    }

    @Override
    public void setConfig(String SessionID, String parameter, String value) throws RemoteException {
        sm.checkSession(SessionID);
        this.logger.log("Setting config of " + parameter + " to " + value);
    }
}
