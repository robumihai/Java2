package org.example.model;

import java.sql.Date;

// modelul orientat pe obiect pentru tabelul movies
public class Movie {
    private int id;
    private String title;
    private Date releaseDate;
    private int duration;
    private double score;
    private String genreName; // luat din view

    // constructor gol
    public Movie() {}

    // constructor complet
    public Movie(String title, Date releaseDate, int duration, double score, String genreName) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
        this.genreName = genreName;
    }

    // getteri si setteri necesari pentru freemarker
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }
}