package org.example.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa care reprezinta o resursa bibliografica (carte, articol web, etc.).
 * Contine identificatori de baza si un map pentru proprietati aditionale.
 */
public class Resource {
    private String id;
    private String title;
    private String location; // Poate fi cale locala sau URL
    private Map<String, String> properties = new HashMap<>();

    /**
     * Constructor pentru initializarea unei resurse noi.
     * @param id Identificatorul unic
     * @param title Titlul resursei
     * @param location Calea in sistemul de fisiere sau adresa Web
     */
    public Resource(String id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    /**
     * Adauga o proprietate suplimentara resursei (ex: author, year).
     * @param key Numele proprietatii
     * @param value Valoarea proprietatii
     */
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", properties=" + properties +
                '}';
    }
}