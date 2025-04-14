package model;

public class Reservation {

    private String nom;
    private String email;
    private String type;
    private String date;
    private String heure;
    private String code;

    // Constructeur pour initialiser une réservation
    public Reservation(String nom, String email, String type, String date, String heure, String code) {
        this.nom = nom;
        this.email = email;
        this.type = type;
        this.date = date;
        this.heure = heure;
        this.code = code;
    }

    // Getters pour récupérer les informations de la réservation
    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getCode() {
        return code;
    }
}
