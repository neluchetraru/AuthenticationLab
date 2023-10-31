import Server.Auth.AuthService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException,  NotBoundException, RemoteException {
        try{
            //PrinterService printerService = (PrinterService) Naming.lookup("rmi://localhost:5099/printer");
            AuthService authService = (AuthService) Naming.lookup("rmi://localhost:5100/auth");
            System.out.println("Welcome to the PrinterClient\nWhat would you like to do?");
            while(true) {
                System.out.println("1. Log in");
                System.out.println("2. Register");
                System.out.print("Please input either '1' or '2': ");
                Scanner scanner = new Scanner(System.in);

                String option = scanner.nextLine();
                switch(option) {
                    case "1":
                        System.out.print("Input user name: ");
                        String userNameLI = scanner.nextLine();
                        System.out.print("Input password: ");
                        String passwordLI = scanner.nextLine();
                        try {
                            authService.login(userNameLI, passwordLI);
                            break;
                        } catch (RemoteException e) {
                            System.out.println(e.detail.getMessage());
                            break;
                        }
                    case "2":
                        System.out.print("Input user name: ");
                        String userNameSU = scanner.nextLine();
                        System.out.print("Input password: ");
                        String passwordSU = scanner.nextLine();
                        try {
                            authService.signup(userNameSU, passwordSU);
                            break;
                        } catch (RemoteException e ) {

                            System.out.println(e.detail.getMessage());
                            

                            break;
                        }
                    }

                }
            }
        catch (RemoteException e){
            System.err.println(e.detail.getMessage());
        }
    }
}
