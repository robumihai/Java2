package org.example.command;

import org.example.exception.CatalogException;
import org.example.model.Resource;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class ViewCommand implements Command {
    private Resource resource;

    public ViewCommand(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void execute() throws CatalogException {
        if (resource == null) {
            throw new CatalogException("Resursa specificata nu exista si nu poate fi deschisa.");
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            String location = resource.getLocation();

            if (location.toLowerCase().startsWith("http")) {
                desktop.browse(new URI(location));
            } else {
                File file = new File(location);
                if (!file.exists()) {
                    throw new CatalogException("Fisierul nu exista fizic la: " + location);
                }
                desktop.open(file);
            }
        } catch (Exception e) {
            throw new CatalogException("Eroare la deschiderea resursei: " + resource.getTitle(), e);
        }
    }
}