package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clasa Singleton responsabila de gestionarea conexiunii cu baza de date.
 * Ne asigura ca exista o singura conexiune activa la nivel de aplicatie.
 */
public class Database {
    // Schimba cu datele tale din pgAdmin
    private static final String URL = "jdbc:postgresql://localhost:5432/movies_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola_mea_secreta";

    private static Connection connection = null;

    // Constructor privat pentru a preveni instantierea din afara
    private Database() {}

    /**
     * Metoda care returneaza conexiunea activa. Daca nu exista, o creeaza.
     * @return Conexiunea la baza de date
     * @throws SQLException Daca datele de conectare sunt gresite
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Inchide conexiunea la baza de date in mod controlat.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Eroare la inchiderea conexiunii: " + e.getMessage());
        }
    }
}