package org.example.model;

import java.sql.Date;

/**
 * modelul care reprezinta un film in json.
 */
public class Movie {
    private int id;
    private String title;
    private Date releaseDate;
    private int duration;
    private double score;

    public Movie() {}

    // getteri si setteri (obligatorii ca spring boot sa poata transforma obiectul in json)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}