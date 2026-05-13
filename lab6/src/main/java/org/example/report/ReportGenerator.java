package org.example.report;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.example.model.Movie;
import org.example.util.Database;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// clasa care genereaza raportul html folosind freemarker si un view sql
public class ReportGenerator {

    // metoda care extrage datele si creeaza fisierul
    public void generateReport() {
        List<Movie> movies = new ArrayList<>();

        // citim datele din view-ul stocat in baza de date (cerinta homework)
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM movie_report_view");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setReleaseDate(rs.getDate("release_date"));
                m.setDuration(rs.getInt("duration"));
                m.setScore(rs.getDouble("score"));
                m.setGenreName(rs.getString("genre"));
                movies.add(m);
            }

            // pregatim sablonul html ca un simplu string
            String templateStr = "<html><head><title>raport filme</title></head><body>" +
                    "<h1>catalogul filmelor (din view)</h1><table border='1'>" +
                    "<tr><th>titlu</th><th>lansare</th><th>durata (min)</th><th>scor</th><th>gen</th></tr>" +
                    "<#list movies as m>" +
                    "<tr><td>${m.title}</td><td>${m.releaseDate}</td><td>${m.duration}</td><td>${m.score}</td><td>${m.genreName}</td></tr>" +
                    "</#list></table></body></html>";

            File tempFtl = new File("movies_template.ftl");
            try (FileWriter fw = new FileWriter(tempFtl)) {
                fw.write(templateStr);
            }

            // configuram freemarker
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setDirectoryForTemplateLoading(new File("."));
            Template template = cfg.getTemplate("movies_template.ftl");

            // mapam datele pentru html
            Map<String, Object> data = new HashMap<>();
            data.put("movies", movies);

            // generam fisierul final
            File reportFile = new File("raport_filme.html");
            try (FileWriter fw = new FileWriter(reportFile)) {
                template.process(data, fw);
            }

            System.out.println("raport generat cu succes. se deschide in browser...");
            Desktop.getDesktop().browse(reportFile.toURI());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}