import Server.Auth.AuthService;
import Server.Printer.PrinterService;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        try {
            System.setProperty("javax.net.ssl.trustStore", "Cert/clientTruststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");

            Registry registry = LocateRegistry.getRegistry("localhost", 5101, new SslRMIClientSocketFactory());

            PrinterService printerService = (PrinterService) registry.lookup("printer");
            AuthService authService = (AuthService) registry.lookup("auth");
            MainMenu: while (true) {
                System.out.println("Welcome to the PrinterClient\nWhat would you like to do?");
                String sessionID = null;
                LoginLoop: while (true) {
                    System.out.println("1. Log in");
                    System.out.println("2. Register");
                    System.out.println("3. Quit");
                    System.out.print("Please input either '1', '2', or '3': ");
                    Scanner scanner = new Scanner(System.in);

                    String option = scanner.nextLine();
                    switch (option) {
                        case "1":
                            System.out.print("Input user name: ");
                            String userNameLI = scanner.nextLine();
                            System.out.print("Input password: ");
                            String passwordLI = scanner.nextLine();
                            try {
                                sessionID = authService.login(userNameLI, passwordLI);
                                break LoginLoop;
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
                                break LoginLoop;
                            } catch (RemoteException e) {
                                System.out.println(e.detail.getMessage());
                                break;
                            }
                        case "3":
                            break MainMenu;
                    }
                }

                ClientLoop: while (true) {
                    System.out.println("You are logged in\nWhat would you like to do?");
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
                    System.out.print("Please choose an option: ");

                    Scanner scanner = new Scanner(System.in);

                    String option = scanner.nextLine();
                    switch (option) {
                        case "1":
                            authService.logout(sessionID);
                            break ClientLoop;
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
                            Scanner scanner1 = new Scanner(System.in);
                            System.out.print("Input filename: ");
                            String filename = scanner1.nextLine();
                            System.out.print("Input printer: ");
                            String printer = scanner1.nextLine();

                            printerService.print(sessionID, filename, printer);
                            break;
                        case "6":
                            System.out.print("Input printer: ");
                            String printer1 = scanner.nextLine();
                            printerService.queue(sessionID, printer1);
                            break;
                        case "7":
                            System.out.print("Input printer: ");
                            String printer2 = scanner.nextLine();
                            System.out.print("Input job: ");
                            int job = scanner.nextInt();
                            printerService.topQueue(sessionID, printer2, job);
                            break;
                        case "8":
                            System.out.print("Input printer: ");
                            String printer3 = scanner.nextLine();
                            printerService.status(sessionID, printer3);
                            break;
                        case "9":
                            System.out.print("Input parameter: ");
                            String parameter = scanner.nextLine();
                            printerService.readConfig(sessionID, parameter);
                            break;
                        case "10":
                            System.out.print("Input parameter: ");
                            String parameter1 = scanner.nextLine();
                            System.out.print("Input value: ");
                            String value = scanner.nextLine();
                            printerService.setConfig(sessionID, parameter1, value);
                            break;
                    }
                }

            }
        } catch (RemoteException e) {
            System.err.println(e.detail.getMessage());
        }
    }
}
