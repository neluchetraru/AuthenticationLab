package Server.Printer;

import Server.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {
    private Logger logger;

    public PrinterServant(Logger logger) throws RemoteException {
        super();
        this.logger = logger;
    }
    public void echo(String input) throws RemoteException{
        this.logger.log(input);
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {
        this.logger.log("Printing " + filename + " on " + printer);
    }

    @Override
    public void queue(String printer) throws RemoteException {
        this.logger.log("Queueing " + printer);
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        this.logger.log("Topping " + printer + " with job " + job);
    }

    @Override
    public void start() throws RemoteException {
        this.logger.log("Starting the print server");
    }

    @Override
    public void stop() throws RemoteException {
        this.logger.log("Stopping the print server");
    }

    @Override
    public void restart() throws RemoteException {
        this.logger.log("Restarting the print server");
    }

    @Override
    public void status(String printer) throws RemoteException {
        this.logger.log("Status of " + printer);
    }

    @Override
    public void readConfig(String parameter) throws RemoteException {
        this.logger.log("Reading config of " + parameter);
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        this.logger.log("Setting config of " + parameter + " to " + value);
    }
}
