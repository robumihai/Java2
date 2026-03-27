package org.example.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.CatalogException;
import org.example.repository.Catalog;
import java.io.File;
import java.io.IOException;

public class SaveCommand implements Command {
    private Catalog catalog;
    private String path;

    public SaveCommand(Catalog catalog, String path) {
        this.catalog = catalog;
        this.path = path;
    }

    @Override
    public void execute() throws CatalogException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(path), catalog);
            System.out.println("Catalog salvat cu succes in: " + path);
        } catch (IOException e) {
            throw new CatalogException("Nu am putut salva catalogul in fisier.", e);
        }
    }
}