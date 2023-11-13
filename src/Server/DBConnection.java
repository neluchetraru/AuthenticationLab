package Server;

import java.sql.*;

public class DBConnection {
    private Connection connection;
    DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //using fallbackToSystemKeyStore=false because of problems with Amazon RDS certificate
            this.connection = DriverManager.getConnection("jdbc:mysql://database-1.ck0pflmse1ey.eu-north-1.rds.amazonaws.com:3306/PrinterServer2?fallbackToSystemKeyStore=false&verifyServerCertificate=true&useSSL=true&requireSSL=true", "admin_DTU", "ay3rqyX!TANHMK5jBJJG$F98#k3C9sbe");
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    Connection getConnection() {
        return this.connection;
    }

    void closeConnection() {
        try {
            this.connection.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
