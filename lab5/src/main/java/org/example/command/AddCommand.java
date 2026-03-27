package org.example.command;

import org.example.model.Resource;
import org.example.repository.Catalog;

public class AddCommand implements Command {
    private Catalog catalog;
    private Resource resource;

    public AddCommand(Catalog catalog, Resource resource) {
        this.catalog = catalog;
        this.resource = resource;
    }

    @Override
    public void execute() {
        catalog.getResources().add(resource);
    }
}