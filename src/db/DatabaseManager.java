package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/reservation_db";
    // private static final String USER = "root";
    // private static final String PASSWORD = "200408"; // ajoute ton mot de passe si tu en as un

    private static final String USER = "kevin";
    private static final String PASSWORD = "reservation_billet_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}