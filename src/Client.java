import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/auth");

        System.out.println("--- " + service.echo("Hey server"));
        System.out.println("Please login before continuing.");
    }
}
