package org.example.main;

import org.example.dao.GenreDAO;
import org.example.util.Database;
import java.sql.SQLException;

/**
 * Clasa principala care testeaza operatiunile JDBC pentru Compulsory Lab 6.
 */
public class Main {
    public static void main(String[] args) {
        try {
            GenreDAO genres = new GenreDAO();

            System.out.println("Incerc sa ma conectez la baza de date si sa adaug date...");

            // 1. Inseram cateva genuri noi (Atentie: daca rulezi de 2 ori o sa dea eroare
            // de UNIQUE constraint, deci prima oara ruleaza asa, apoi le comentezi)
            genres.create("Action");
            genres.create("Drama");
            genres.create("Comedy");

            // 2. Cautam genurile folosind DAO si afisam rezultatul
            int actionId = genres.findByName("Action");
            System.out.println("Genul 'Action' a fost gasit cu ID-ul: " + actionId);

            String genreName = genres.findById(actionId);
            System.out.println("Genul cu ID-ul " + actionId + " se numeste: " + genreName);

            System.out.println("Toate testele au trecut cu succes!");

        } catch (SQLException e) {
            System.err.println("O eroare SQL a aparut: " + e.getMessage());
        } finally {
            // Indiferent ce se intampla, inchidem conexiunea la final
            Database.closeConnection();
        }
    }
}