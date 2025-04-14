package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Reservation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    // Méthode pour générer un ticket PDF pour une réservation
    public static String generateTicket(Reservation reservation) {
        // Chemin du fichier PDF qui sera généré
        String pdfFilePath = "ticket_" + reservation.getCode() + ".pdf";

        try {
            Document document = new Document();  // Création du document PDF
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));  // Création du writer pour le fichier PDF

            document.open();  // Ouverture du document PDF

            // Ajout du contenu au document PDF
            document.add(new Paragraph("Ticket de Réservation"));
            document.add(new Paragraph("Nom: " + reservation.getNom()));
            document.add(new Paragraph("Email: " + reservation.getEmail()));
            document.add(new Paragraph("Type de transport: " + reservation.getType()));
            document.add(new Paragraph("Date: " + reservation.getDate()));
            document.add(new Paragraph("Heure: " + reservation.getHeure()));
            document.add(new Paragraph("Code: " + reservation.getCode()));

            document.close();  // Fermeture du document

        } catch (DocumentException | IOException e) {
            e.printStackTrace();  // Gestion des exceptions si la génération échoue
        }

        return pdfFilePath;  // Retourne le chemin du fichier PDF généré
    }
}
