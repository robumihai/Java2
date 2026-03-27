package org.example.dao;

import org.example.util.Database;
import java.sql.*;

/**
 * Clasa DAO (Data Access Object) pentru tabelul 'genres'.
 * Contine logica pentru insertii si selectii.
 */
public class GenreDAO {

    /**
     * Insereaza un gen nou in baza de date.
     * @param name Numele genului (ex: "Action")
     * @throws SQLException Daca apare o eroare de scriere in baza de date
     */
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        // Folosim PreparedStatement pentru a preveni SQL Injection
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO genres (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    /**
     * Gaseste id-ul unui gen cunoscandu-i numele.
     * @param name Numele genului cautat
     * @return ID-ul genului sau null daca nu a fost gasit
     * @throws SQLException Daca apare o eroare de citire
     */
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id FROM genres WHERE name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Daca gasim inregistrarea, ii returnam id-ul
                return rs.next() ? rs.getInt("id") : null;
            }
        }
    }

    /**
     * Gaseste numele unui gen cunoscandu-i id-ul.
     * @param id ID-ul genului cautat
     * @return Numele genului sau null daca nu exista
     * @throws SQLException Daca apare o eroare de citire
     */
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT name FROM genres WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getString("name") : null;
            }
        }
    }
}