package gui;

import db.ReservationDAO;
import model.Reservation;
import utils.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.List;
import java.util.UUID;

public class ReservationUI extends JFrame {

    // Déclaration des composants de l'interface utilisateur
    private JComboBox<String> typeBox;
    private JTextField nameField, emailField;
    private JTextField dateField, timeField;
    private JTextArea reservationListArea;

    // Constructeur de l'interface utilisateur
    public ReservationUI() {
        setTitle("Système de Réservation");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Ajout des composants à l'interface
        add(new JLabel("Type de Transport:"));
        typeBox = new JComboBox<>(new String[]{"Cinéma", "Bus", "Avion"});
        add(typeBox);

        add(new JLabel("Nom:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Date (yyyy-MM-dd):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Heure (HH:mm):"));
        timeField = new JTextField();
        add(timeField);

        // Bouton pour effectuer une réservation
        JButton reserveButton = new JButton("Réserver");
        reserveButton.addActionListener(this::handleReservation);  // Action pour réserver
        add(reserveButton);

        // Bouton pour afficher les réservations existantes
        JButton showReservationsButton = new JButton("Afficher Réservations");
        showReservationsButton.addActionListener(this::handleShowReservations);  // Action pour afficher les réservations
        add(showReservationsButton);

        // Zone de texte pour afficher les réservations existantes
        reservationListArea = new JTextArea();
        reservationListArea.setEditable(false);  // Non modifiable
        JScrollPane scrollPane = new JScrollPane(reservationListArea);  // Ajout d'un défilement
        add(scrollPane);

        setVisible(true);  // Affichage de l'interface
    }

    // Méthode pour gérer la réservation
    private void handleReservation(ActionEvent e) {
        // Récupération des données saisies dans l'interface utilisateur
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText();
        String email = emailField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String code = UUID.randomUUID().toString().substring(0, 8);  // Génération d'un code unique

        // Création d'un objet Reservation avec les données
        Reservation reservation = new Reservation(name, email, type, date, time, code);

        try {
            // Ajout de la réservation dans la base de données
            ReservationDAO.ajouterReservation(name, email, type, date, time);

            // Affichage d'un message de succès à l'utilisateur
            JOptionPane.showMessageDialog(this, "Réservation réussie !");

            // Génération du ticket PDF pour la réservation
            String pdfFilePath = PDFGenerator.generateTicket(reservation);

            // Tentative d'ouverture automatique du fichier PDF généré
            File pdfFile = new File(pdfFilePath);
            if (pdfFile.exists()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(pdfFile);  // Ouvre le PDF avec le programme par défaut
            } else {
                JOptionPane.showMessageDialog(this, "Erreur: le fichier PDF n'a pas été généré.");
            }
        } catch (Exception ex) {
            // Gestion des erreurs si la réservation échoue
            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation : " + ex.getMessage());
        }
    }

    // Méthode pour afficher toutes les réservations existantes
    private void handleShowReservations(ActionEvent e) {
        try {
            // Récupération des réservations depuis la base de données
            List<String> reservations = ReservationDAO.afficherReservations();

            // Affichage des réservations dans la zone de texte
            reservationListArea.setText("");  // Efface les anciennes réservations
            for (String reservation : reservations) {
                reservationListArea.append(reservation + "\n");  // Ajoute chaque réservation à la zone de texte
            }
        } catch (Exception ex) {
            // Gestion des erreurs lors de la récupération des réservations
            JOptionPane.showMessageDialog(this, "Erreur lors de l'affichage des réservations : " + ex.getMessage());
        }
    }

}
