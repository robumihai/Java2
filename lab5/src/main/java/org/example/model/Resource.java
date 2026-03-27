package org.example.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa care reprezinta o resursa bibliografica.
 */
public class Resource {
    private String id;
    private String title;
    private String location;
    private Map<String, String> properties = new HashMap<>();

    // Constructor gol, obligatoriu pentru Jackson (salvare/incarcare JSON)
    public Resource() {}

    public Resource(String id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    // Getteri si setteri (Obligatorii pentru Jackson)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Map<String, String> getProperties() { return properties; }
    public void setProperties(Map<String, String> properties) { this.properties = properties; }

    @Override
    public String toString() {
        return "Resource [id=" + id + ", title=" + title + ", location=" + location + "]";
    }
}