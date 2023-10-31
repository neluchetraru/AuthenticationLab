import Server.Auth.AuthService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
                SwitchLoop: while(true){
                    String option = scanner.nextLine();
                    switch(option) {
                        case "1":
                            System.out.print("Input user name: ");
                            String userNameLI = scanner.nextLine();
                            System.out.print("Input password: ");
                            String passwordLI = scanner.nextLine();
                            try {
                                authService.login(userNameLI, passwordLI);
                                break SwitchLoop;
                            } catch (Exception e) {
                                System.out.println("Incorrect credentials. Try again");
                                break SwitchLoop;
                            }
                        case "2":
                            System.out.print("Input user name: ");
                            String userNameSU = scanner.nextLine();
                            System.out.print("Input password: ");
                            String passwordSU = scanner.nextLine();
                            authService.signup(userNameSU, passwordSU);
                            break SwitchLoop;
                    }

                }
            }
        }
        catch (RemoteException e){
            System.err.println(e.detail.getMessage());
        }
        catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }


    }
}
