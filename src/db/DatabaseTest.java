import db.DatabaseManager;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("Connexion réussie à la base de données !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
