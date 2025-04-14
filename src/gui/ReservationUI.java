package gui;

import db.ReservationDAO;
import model.Reservation;
import utils.PDFGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.UUID;

public class ReservationUI extends JFrame {

    private JComboBox<String> typeBox;
    private JTextField nameField, emailField;
    private JTextField dateField, timeField;
    private JTextArea reservationListArea;

    public ReservationUI() {
        setTitle("🎟️ Système de Réservation");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec padding et BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30)); // marges intérieures

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Color accentColor = new Color(66, 135, 245); // bleu doux
        Color backgroundColor = new Color(245, 248, 252);

        // Labels et champs stylés
        mainPanel.setBackground(backgroundColor);

        mainPanel.add(createLabeledField("Type de Transport:", typeBox = new JComboBox<>(new String[]{"Cinéma", "Bus", "Avion"}), labelFont));
        mainPanel.add(createLabeledField("Nom:", nameField = new JTextField(), labelFont));
        mainPanel.add(createLabeledField("Email:", emailField = new JTextField(), labelFont));
        mainPanel.add(createLabeledField("Date (yyyy-MM-dd):", dateField = new JTextField(), labelFont));
        mainPanel.add(createLabeledField("Heure (HH:mm):", timeField = new JTextField(), labelFont));

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton reserveButton = new JButton("Réserver");
        JButton showReservationsButton = new JButton("Afficher Réservations");

        styleButton(reserveButton, accentColor);
        styleButton(showReservationsButton, new Color(34, 170, 89)); // vert

        reserveButton.addActionListener(this::handleReservation);
        showReservationsButton.addActionListener(this::handleShowReservations);

        buttonPanel.add(reserveButton);
        buttonPanel.add(showReservationsButton);
        mainPanel.add(buttonPanel);

        // Zone de texte pour les réservations
        reservationListArea = new JTextArea(8, 40);
        reservationListArea.setEditable(false);
        reservationListArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        reservationListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reservationListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📄 Réservations"));

        mainPanel.add(scrollPane);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JComponent inputField, Font labelFont) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setMaximumSize(new Dimension(500, 50));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        inputField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        inputField.setPreferredSize(new Dimension(200, 30));

        panel.add(label, BorderLayout.WEST);
        panel.add(inputField, BorderLayout.CENTER);

        return panel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void handleReservation(ActionEvent e) {
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText();
        String email = emailField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String code = UUID.randomUUID().toString().substring(0, 8);

        Reservation reservation = new Reservation(name, email, type, date, time, code);

        try {
            ReservationDAO.ajouterReservation(name, email, type, date, time);
            JOptionPane.showMessageDialog(this, "✅ Réservation réussie !");
            String pdfFilePath = PDFGenerator.generateTicket(reservation);
            File pdfFile = new File(pdfFilePath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur: le fichier PDF n'a pas été généré.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation : " + ex.getMessage());
        }
    }

    private void handleShowReservations(ActionEvent e) {
        try {
            List<String> reservations = ReservationDAO.afficherReservations();
            reservationListArea.setText("");
            for (String reservation : reservations) {
                reservationListArea.append(reservation + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'affichage des réservations : " + ex.getMessage());
        }
    }
}
