package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/reservation_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "200408";

    private static final String USER = "kevin";
    private static final String PASSWORD = "reservation_billet_password";

    // Méthode pour obtenir la connexion à la base de données
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour ajouter une nouvelle réservation dans la base de données
    public static void ajouterReservation(String nom, String email, String type, String date, String heure) throws SQLException {
        String query = "INSERT INTO reservations (nom, email, type_transport, date_reservation, heure) VALUES (?, ?, ?, ?, ?)";

        // Connexion à la base de données et exécution de la requête
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, type);
            stmt.setString(4, date);
            stmt.setString(5, heure);

            stmt.executeUpdate();  // Exécution de la requête
        }
    }

    // Méthode pour afficher toutes les réservations enregistrées dans la base de données
    public static List<String> afficherReservations() throws SQLException {
        List<String> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        // Connexion à la base de données et exécution de la requête
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Pour chaque réservation, on construit une chaîne avec les détails
                String reservation = "ID: " + rs.getInt("id") +
                        ", Nom: " + rs.getString("nom") +
                        ", Email: " + rs.getString("email") +
                        ", Type: " + rs.getString("type_transport") +
                        ", Date: " + rs.getDate("date_reservation") +
                        ", Heure: " + rs.getTime("heure");
                reservations.add(reservation);  // Ajout de la réservation à la liste
            }
        }
        return reservations;  // Retourne la liste des réservations
    }
}
