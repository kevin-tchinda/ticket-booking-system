package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

public class PDFGenerator {
    public static void genererPDF(String nom, String email, String type, String date, String heure) {
        try {
            Document document = new Document();
            String filename = "ticket_" + nom + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            document.add(new Paragraph("=== TICKET DE RÉSERVATION ==="));
            document.add(new Paragraph("Nom : " + nom));
            document.add(new Paragraph("Email : " + email));
            document.add(new Paragraph("Transport : " + type));
            document.add(new Paragraph("Date : " + date));
            document.add(new Paragraph("Heure : " + heure));

            document.close();
            System.out.println("PDF généré avec succès !");

            // Ouvrir le fichier PDF automatiquement (Windows)
            if (Desktop.isDesktopSupported()) {
                File pdfFile = new File(filename);
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (Exception e) {
            System.err.println("Erreur PDF : " + e.getMessage());
        }
    }
}
