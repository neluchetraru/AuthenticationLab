package Server;

import Server.Auth.AuthServant;
import Server.Printer.PrinterServant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;

public class Server {


    public static void main(String[] args) throws RemoteException {

        DBConnection db = new DBConnection();
        Connection connection = db.getConnection();
        Registry registry = LocateRegistry.createRegistry(5100);
        registry.rebind("hello", new PrinterServant(Logger.getInstance()));
        registry.rebind("auth", new AuthServant(connection));

    } 

}
