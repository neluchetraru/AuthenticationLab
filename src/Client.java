import Server.Auth.AuthService;
import Server.Auth.Session;
import Server.Printer.PrinterService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException,  NotBoundException, RemoteException {
        try{
            PrinterService printerService = (PrinterService) Naming.lookup("rmi://localhost:5099/printer");
            AuthService authService = (AuthService) Naming.lookup("rmi://localhost:5100/auth");

            System.out.println("Welcome to the PrinterClient\nWhat would you like to do?");
            String sessionID = null;
            loop: while(true) {
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
                            sessionID = authService.login(userNameLI, passwordLI);
                            break loop;
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
                            sessionID = authService.signup(userNameSU, passwordSU);
                            break loop;
                        } catch (RemoteException e ) {
                            System.out.println(e.detail.getMessage());
                            break;
                        }
                }
            }

            loop2: while(true){
                System.out.println("1. Log out");
                System.out.println("2. Start printer");
                System.out.println("3. Stop printer");
                System.out.println("4. Restart printer");
                System.out.println("5. Print");
                System.out.println("6. Queue");
                System.out.println("7. topQueue");
                System.out.println("8. Printer Status");
                System.out.println("9. Read config");
                System.out.println("10. Set config");
                System.out.println("Please choose an option: ");
                Scanner scanner = new Scanner(System.in);

                String option = scanner.nextLine();
                switch(option) {
                    case "1":
                        authService.logout(sessionID);
                        break loop2;
                    case "2":
                        printerService.start(sessionID);
                        break;
                    case "3":
                        printerService.stop(sessionID);
                        break;
                    case "4":
                        printerService.restart(sessionID);
                        break;
                    case "5":
                        printerService.print(sessionID,"test.txt", "printer1");
                        break;
                    case "6":
                        printerService.queue(sessionID,"printer1");
                        break;
                    case "7":
                        printerService.topQueue(sessionID,"printer1", 1);
                        break;
                    case "8":
                        printerService.status(sessionID,"printer1");
                        break;
                    case "9":
                        printerService.readConfig(sessionID,"wireless");
                        break;
                    case "10":
                        printerService.setConfig(sessionID,"wireless", "true");
                        break;
                }
            }

        }
        catch (RemoteException e){
            System.err.println(e.detail.getMessage());
        }
    }
}
