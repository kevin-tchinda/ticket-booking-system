package DAO;

import db.ReservationDAO;
import utils.PDFGenerator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationDAO reservationDAO = new ReservationDAO(); // utile si tu veux utiliser une instance
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Réservation de billet ===");
        System.out.print("Entrez le nom : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez l'email : ");
        String email = scanner.nextLine();

        System.out.print("Type de transport (Cinéma / Bus / Avion) : ");
        String typeTransport = scanner.nextLine();

        System.out.print("Date de réservation (format YYYY-MM-DD) : ");
        String dateReservation = scanner.nextLine();

        System.out.print("Heure (format HH:MM:SS) : ");
        String heure = scanner.nextLine();

        try {
            reservationDAO.ajouterReservation(nom, email, typeTransport, dateReservation, heure);
            System.out.println("Réservation enregistrée avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la réservation : " + e.getMessage());
        }

        ReservationAfficher.afficherReservations();

        PDFGenerator.genererPDF(nom, email, typeTransport, dateReservation, heure);
        scanner.close();
    }
}
