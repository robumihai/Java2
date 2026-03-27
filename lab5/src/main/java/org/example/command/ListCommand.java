package org.example.command;

import org.example.model.Resource;
import org.example.repository.Catalog;

public class ListCommand implements Command {
    private Catalog catalog;

    public ListCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() {
        System.out.println("Continutul catalogului '" + catalog.getName() + "':");
        for (Resource r : catalog.getResources()) {
            System.out.println(" - " + r.getTitle() + " (Locatie: " + r.getLocation() + ")");
        }
    }
}