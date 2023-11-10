package Server.Printer;

import Server.Auth.SessionManager;
import Server.Logger;

import java.rmi.RemoteException;

public class PrinterServant implements PrinterService {
    private final Logger logger;
    SessionManager sm;
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
        sm.checkSession(SessionID, "print");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"print\" on file " + filename + " with printer " + printer);
    }

    @Override
    public void queue(String SessionID, String printer) throws RemoteException {
        sm.checkSession(SessionID, "queue");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"queue\" with printer " + printer);
    }

    @Override
    public void topQueue(String SessionID, String printer, int job) throws RemoteException {
        sm.checkSession(SessionID, "topQueue");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"topQueue\" with printer " + printer + " with job " + job);
    }

    @Override
    public void start(String SessionID)  throws RemoteException {
        sm.checkSession(SessionID, "start");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"start\"");
    }

    @Override
    public void stop(String SessionID) throws RemoteException {
        sm.checkSession(SessionID, "stop");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"stop\"");
    }

    @Override
    public void restart(String SessionID) throws RemoteException {
        sm.checkSession(SessionID, "restart");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"restart\"");
    }

    @Override
    public void status(String SessionID, String printer) throws RemoteException {
        sm.checkSession(SessionID, "status");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"status\" with printer " + printer);
    }

    @Override
    public void readConfig(String SessionID, String parameter) throws RemoteException {
        sm.checkSession(SessionID, "readConfig");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"readConfig\" on parameter " + parameter);
    }

    @Override
    public void setConfig(String SessionID, String parameter, String value) throws RemoteException {
        sm.checkSession(SessionID, "setConfig");
        this.logger.log("User \""+ sm.getUser(SessionID) + "\" ran method \"setConfig\" on parameter " + parameter + " to value " + value);
    }
}
