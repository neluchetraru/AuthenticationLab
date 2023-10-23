import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println("--- " + service.echo("Hey server"));
    }
}
