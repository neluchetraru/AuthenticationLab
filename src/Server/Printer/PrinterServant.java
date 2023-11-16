package Server.Printer;

import Server.Auth.SessionManager;
import Server.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class PrinterServant implements PrinterService {
    private final Logger logger;
    SessionManager sm = null;
    private Connection connection = null;

    public PrinterServant(Logger logger, SessionManager sm, Connection connection) throws RemoteException {
        super();
        this.logger = logger;
        this.sm = sm;
        this.connection = connection;
    }

    public void echo(String input) throws RemoteException {
        this.logger.log(input);
    }

    @Override
    public void print(String SessionID, String filename, String printer) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "print")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"print\" on file " + filename + " with printer " + printer);
        } else {
            System.out.println("Not allowed");
            this.logger.log("User \"" + userName + "\" tried to run method \"print\" on file " + filename + " with printer " + printer + " but was denied permission");
        }
    }

    private boolean acquirePermission(String userName, String function) throws RemoteException {

            String sql = "SELECT * FROM UsersP2 NATURAL JOIN Roles NATURAL JOIN Permissions WHERE UserName = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String permission = result.getString("Permission");
                if (permission.equals(function)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RemoteException("Something went wrong");
        }

        return false;
    }

    @Override
    public void queue(String SessionID, String printer) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "queue")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"queue\" with printer " + printer);
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"queue\" with printer " + printer + " but was denied permission");
            throw new RemoteException("Not allowed");
        }
    }

    @Override
    public void topQueue(String SessionID, String printer, int job) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "topQueue")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"topQueue\" with printer " + printer + " with job " + job);
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"topQueue\" with printer " + printer + " with job " + job + " but was denied permission");
            throw new RemoteException("Not allowed");
        }
    }

    @Override
    public void start(String SessionID) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "start")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"start\"");
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"start\" but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }

    @Override
    public void stop(String SessionID) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "stop")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"stop\"");
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"stop\" but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }

    @Override
    public void restart(String SessionID) throws RemoteException {
        if (acquirePermission(sm.getUser(SessionID), "restart")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + sm.getUser(SessionID) + "\" ran method \"restart\"");
        } else {
            this.logger.log("User \"" + sm.getUser(SessionID) + "\" tried to run method \"restart\" but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }

    @Override
    public void status(String SessionID, String printer) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "status")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"status\" with printer " + printer);
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"status\" with printer " + printer + " but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }

    @Override
    public void readConfig(String SessionID, String parameter) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "readConfig")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"readConfig\" on parameter " + parameter);
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"readConfig\" on parameter " + parameter + " but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }

    @Override
    public void setConfig(String SessionID, String parameter, String value) throws RemoteException {
        String userName = sm.getUser(SessionID);
        if (acquirePermission(userName, "setConfig")) {
            sm.checkSession(SessionID);
            this.logger.log("User \"" + userName + "\" ran method \"setConfig\" on parameter " + parameter + " with value " + value);
        } else {
            this.logger.log("User \"" + userName + "\" tried to run method \"setConfig\" on parameter " + parameter + " with value " + value + " but was denied permission");
            throw new RemoteException("Not allowed");

        }
    }
}
