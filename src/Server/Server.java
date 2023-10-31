package Server;

import Server.Auth.AuthServant;
import Server.Auth.SessionManager;
import Server.Printer.PrinterServant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;

public class Server {


    public static void main(String[] args) throws RemoteException {

        DBConnection db = new DBConnection();
        Connection connection = db.getConnection();
        SessionManager sm = new SessionManager();
        Registry registry = LocateRegistry.createRegistry(5100);
        registry.rebind("printer", new PrinterServant(Logger.getInstance(), sm));
        registry.rebind("auth", new AuthServant(connection, sm));

    } 

}
