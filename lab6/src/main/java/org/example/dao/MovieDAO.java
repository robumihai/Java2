package org.example.dao;

import org.example.util.Database;
import java.sql.*;

// clasa dao pentru filme
public class MovieDAO {

    // adaugam un film nou
    public void create(String title, Date releaseDate, int duration, double score, int genreId) throws SQLException {
        // luam o conexiune din pool
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     "INSERT INTO movies (title, release_date, duration, score, genre_id) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, title);
            pstmt.setDate(2, releaseDate);
            pstmt.setInt(3, duration);
            pstmt.setDouble(4, score);
            pstmt.setInt(5, genreId);

            pstmt.executeUpdate();
        }
    }
}