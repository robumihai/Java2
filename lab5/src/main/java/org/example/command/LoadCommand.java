package org.example.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.CatalogException;
import org.example.repository.Catalog;
import java.io.File;
import java.io.IOException;

public class LoadCommand implements Command {
    private String path;
    private Catalog loadedCatalog;

    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute() throws CatalogException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.loadedCatalog = mapper.readValue(new File(path), Catalog.class);
            System.out.println("Catalog incarcat cu succes din: " + path);
        } catch (IOException e) {
            throw new CatalogException("Nu am putut incarca catalogul din fisier.", e);
        }
    }

    public Catalog getLoadedCatalog() {
        return loadedCatalog;
    }
}