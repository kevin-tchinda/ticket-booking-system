package db;

import java.sql.*;

public class ReservationDAO {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/reservation_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "200408";

    public static void ajouterReservation(String nom, String email, String type, String date, String heure) throws Exception {
        // Connexion automatique avec try-with-resources
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Vérifie si une réservation identique existe déjà
            String checkSql = "SELECT COUNT(*) FROM reservations WHERE nom = ? AND email = ? AND type_transport = ? AND date_reservation = ? AND heure = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, nom);
                checkStmt.setString(2, email);
                checkStmt.setString(3, type);
                checkStmt.setDate(4, Date.valueOf(date));
                checkStmt.setTime(5, Time.valueOf(heure));

                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    System.out.println("⚠️ Une réservation identique existe déjà !");
                    return;
                }
            }

            // Insertion de la nouvelle réservation
            String insertSql = "INSERT INTO reservations (nom, email, type_transport, date_reservation, heure) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, nom);
                insertStmt.setString(2, email);
                insertStmt.setString(3, type);
                insertStmt.setDate(4, Date.valueOf(date));
                insertStmt.setTime(5, Time.valueOf(heure));

                int lignesAjoutees = insertStmt.executeUpdate();
                if (lignesAjoutees > 0) {
                    System.out.println("✅ Réservation enregistrée avec succès !");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            throw e;
        }
    }
}
