package org.example.controller;

import org.example.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller-ul rest pentru filme cu operatii crud
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. GET: obtine toate filmele (de la compulsory)
    @GetMapping
    public List<Movie> getAllMovies() {
        return jdbcTemplate.query("SELECT * FROM movies", (rs, rowNum) -> {
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setTitle(rs.getString("title"));
            movie.setReleaseDate(rs.getDate("release_date"));
            movie.setDuration(rs.getInt("duration"));
            movie.setScore(rs.getDouble("score"));
            return movie;
        });
    }

    // 2. POST: adauga un film nou
    @PostMapping
    public String addMovie(@RequestBody Movie movie) {
        String sql = "INSERT INTO movies (title, release_date, duration, score) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseDate(), movie.getDuration(), movie.getScore());
        return "filmul a fost adaugat cu succes!";
    }

    // 3. PUT: modifica toate detaliile unui film existent
    @PutMapping("/{id}")
    public String updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        String sql = "UPDATE movies SET title=?, release_date=?, duration=?, score=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseDate(), movie.getDuration(), movie.getScore(), id);
        if (rows == 0) throw new RuntimeException("filmul cu id-ul " + id + " nu a fost gasit.");
        return "filmul a fost actualizat complet!";
    }

    // 4. PATCH: modifica doar scorul unui film
    @PatchMapping("/{id}/score")
    public String updateMovieScore(@PathVariable int id, @RequestParam double newScore) {
        String sql = "UPDATE movies SET score=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, newScore, id);
        if (rows == 0) throw new RuntimeException("filmul cu id-ul " + id + " nu a fost gasit.");
        return "scorul a fost actualizat la " + newScore;
    }

    // 5. DELETE: sterge un film
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable int id) {
        String sql = "DELETE FROM movies WHERE id=?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows == 0) throw new RuntimeException("filmul cu id-ul " + id + " nu a fost gasit.");
        return "filmul a fost sters!";
    }
}