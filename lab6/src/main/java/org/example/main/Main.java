package org.example.main;

import org.example.dao.GenreDAO;
import org.example.dao.MovieDAO;
import org.example.report.ReportGenerator;
import org.example.util.Database;

public class Main {
    public static void main(String[] args) {
        try {
            GenreDAO genreDAO = new GenreDAO();
            MovieDAO movieDAO = new MovieDAO();

            System.out.println("conectare reusita. generam raportul...");

            // comentam partea de adaugare ca sa nu mai faca duplicate
            /*
            try {
                genreDAO.create("Sci-Fi");
            } catch (java.sql.SQLException e) {
            }

            Integer genreId = genreDAO.findByName("Sci-Fi");
            if (genreId != null) {
                movieDAO.create("Interstellar", java.sql.Date.valueOf("2014-11-07"), 169, 8.6, genreId);
                movieDAO.create("The Matrix", java.sql.Date.valueOf("1999-03-31"), 136, 8.7, genreId);
            }
            */

            System.out.println("generam raportul html din view...");
            ReportGenerator reportGenerator = new ReportGenerator();
            reportGenerator.generateReport();

            // prindem o exceptie generala, nu doar pe cea de sql
        } catch (Exception e) {
            System.err.println("a aparut o eroare neasteptata: " + e.getMessage());
        } finally {
            Database.closePool();
        }
    }
}