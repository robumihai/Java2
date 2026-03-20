package org.example.repository;

import org.example.exception.CatalogException;
import org.example.model.Resource;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa responsabila cu gestionarea resurselor bibliografice.
 * Permite adaugarea si deschiderea resurselor folosind aplicatia nativa a OS-ului.
 */
public class Catalog {
    private String name;
    private List<Resource> resources = new ArrayList<>();

    public Catalog(String name) {
        this.name = name;
    }

    /**
     * Adauga o resursa in repository-ul catalogului.
     * @param resource Resursa care trebuie adaugata
     */
    public void add(Resource resource) {
        resources.add(resource);
    }

    /**
     * Gaseste o resursa dupa ID pentru a putea fi deschisa.
     * @param id ID-ul resursei cautate
     * @return Resursa gasita sau null daca nu exista
     */
    public Resource findById(String id) {
        return resources.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deschide o resursa folosind clasa Desktop din Java.
     * Trateaza diferit adresele Web (URL) si caile locale (File).
     * @param resource Resursa ce urmeaza a fi deschisa
     * @throws CatalogException Daca sistemul nu suporta operatia sau calea e invalida
     */
    public void open(Resource resource) throws CatalogException {
        // Verificam daca clasa Desktop este suportata pe sistemul de operare curent
        if (!Desktop.isDesktopSupported()) {
            throw new CatalogException("Clasa Desktop nu este suportata de sistemul de operare!");
        }

        Desktop desktop = Desktop.getDesktop();
        String location = resource.getLocation();

        try {
            // Daca locatia incepe cu http/https, o tratam ca pe un link web
            if (location.toLowerCase().startsWith("http")) {
                desktop.browse(new URI(location));
            } else {
                // Altfel o tratam ca pe un fisier local pe disc
                File file = new File(location);
                if (!file.exists()) {
                    throw new CatalogException("Fisierul nu exista la calea specificata: " + location);
                }
                desktop.open(file);
            }
        } catch (Exception e) {
            // Prindem orice eroare de I/O sau URI si o aruncam mai departe ca exceptie custom
            throw new CatalogException("Nu am putut deschide resursa: " + resource.getTitle(), e);
        }
    }
}