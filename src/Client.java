import Server.Auth.AuthService;
import Server.Printer.PrinterService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        //PrinterService printerService = (PrinterService) Naming.lookup("rmi://localhost:5099/printer");

        AuthService authService = (AuthService) Naming.lookup("rmi://localhost:5100/auth");

        authService.signup("admin", "pass");

        //System.out.println("--- " + printerService.echo("Hey server"));
        System.out.println("Please login before continuing.");
    }
}
