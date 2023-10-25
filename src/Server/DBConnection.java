package Server;

import java.sql.*;

public class DBConnection {
    private Connection connection;
    DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/driver", "root", "13795");
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
