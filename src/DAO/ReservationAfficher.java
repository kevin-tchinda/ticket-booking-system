package DAO;

import java.sql.*;

public class ReservationAfficher {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/reservation_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "200408";

    public static void afficherReservations() {
        // Connexion automatique avec try-with-resources
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Requête SQL pour obtenir toutes les réservations
            String sql = "SELECT nom, email, type_transport, date_reservation, heure FROM reservations";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);

                // Vérifie s'il y a des résultats
                if (!rs.next()) {
                    System.out.println("Aucune réservation trouvée.");
                    return;
                }

                // Affiche les réservations
                System.out.println("=== Liste des réservations ===");
                do {
                    String nom = rs.getString("nom");
                    String email = rs.getString("email");
                    String typeTransport = rs.getString("type_transport");
                    Date dateReservation = rs.getDate("date_reservation");
                    Time heure = rs.getTime("heure");

                    System.out.println("Nom : " + nom);
                    System.out.println("Email : " + email);
                    System.out.println("Transport : " + typeTransport);
                    System.out.println("Date : " + dateReservation);
                    System.out.println("Heure : " + heure);
                    System.out.println("---------------------------------");
                } while (rs.next());

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }
}
