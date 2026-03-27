package org.example.repository;

import org.example.model.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Catalogul care tine lista de resurse. Nu mai are logica de add/open aici.
 */
public class Catalog {
    private String name;
    private List<Resource> resources = new ArrayList<>();

    public Catalog() {} // Pentru Jackson

    public Catalog(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Resource> getResources() { return resources; }
    public void setResources(List<Resource> resources) { this.resources = resources; }

    public Resource findById(String id) {
        return resources.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }
}