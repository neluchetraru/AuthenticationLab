import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        Connection connection = db.getConnection();
        System.out.println("Success");
    }
}
