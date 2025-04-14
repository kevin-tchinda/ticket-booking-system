# 🎟️ Ticket Booking System (Java + Swing + MySQL)

Ce projet est un système de réservation de billets pour différents types de transport : **cinéma, bus, avion**.  
Il propose une interface graphique Swing et enregistre les données dans une base MySQL.

---

## ✅ Fonctionnalités principales

- Interface graphique Swing conviviale.
- Ajout de réservations avec nom, email, date, heure et type de transport.
- Génération automatique de billets PDF.
- Affichage des réservations existantes.
- Sauvegarde dans une base de données MySQL.

---

## 💻 Technologies utilisées

- **Java 17**
- **Swing** (interface utilisateur)
- **MySQL** (base de données)
- **JDBC** (connexion DB)
- **iTextPDF** (génération de PDF)
- **GitHub** pour le versionnage et la collaboration

---

## ⚙️ Installation et configuration

### 1. Prérequis
- Java installé (JDK 17 recommandé)
- MySQL installé et un serveur en cours d'exécution
- IntelliJ IDEA ou autre IDE Java

### 2. Base de données

Créer une base de données nommée `reservation_db` puis exécuter la commande SQL suivante :
CREATE TABLE reservations (
id INT PRIMARY KEY AUTO_INCREMENT,
nom VARCHAR(100),
email VARCHAR(100),
type_transport VARCHAR(50),
date_reservation DATE,
heure TIME,
date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




