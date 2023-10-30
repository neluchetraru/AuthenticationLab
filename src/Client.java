import Server.Auth.AuthService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            //PrinterService printerService = (PrinterService) Naming.lookup("rmi://localhost:5099/printer");

            AuthService authService = (AuthService) Naming.lookup("rmi://localhost:5100/auth");

            authService.signup("admin", "123345");

            //System.out.println("--- " + printerService.echo("Hey server"));
            System.out.println("Please login before continuing.");
        }
        catch (RemoteException e){
            System.err.println(e.detail.getMessage());
        }
        catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }


    }
}
