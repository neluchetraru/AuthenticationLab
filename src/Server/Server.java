package Server;

import Server.Auth.AuthServant;
import Server.Auth.SessionManager;
import Server.Printer.PrinterServant;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class Server {
    public static void main(String[] args) throws RemoteException {
        //Set Server KeyStore and TrustStore location and password
        //The KeyStore stores the server certificate used for SSL with the client
        //The TrustStore stores Amazon Certificates for SSL with the Database
        System.setProperty("javax.net.ssl.keyStore", "Cert/serverKeystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "Cert/serverTruststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        //System.setProperty("javax.net.debug", "ssl:handshake"); //To debug SSL/TLS


        DBConnection db = new DBConnection();
        Connection connection = db.getConnection();
        SessionManager sm = new SessionManager();
        Logger logger = Logger.getInstance();

        //Use SSL
        SslRMIClientSocketFactory sslClientSocketFactory = new SslRMIClientSocketFactory();
        SslRMIServerSocketFactory sslServerSocketFactory = new SslRMIServerSocketFactory();

        int serverPort = 5101;
        Registry registry = LocateRegistry.createRegistry(serverPort, sslClientSocketFactory, sslServerSocketFactory);

        PrinterServant printerServant = new PrinterServant(logger, sm);
        AuthServant authServant = new AuthServant(connection, sm);

        UnicastRemoteObject.exportObject(printerServant, 0, sslClientSocketFactory, sslServerSocketFactory);
        UnicastRemoteObject.exportObject(authServant, 0, sslClientSocketFactory, sslServerSocketFactory);


        registry.rebind("printer", printerServant);
        registry.rebind("auth", authServant);

        System.out.println("Server is running on port " + serverPort + "...");
    } 

}
