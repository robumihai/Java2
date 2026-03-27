package org.example.main;

import org.example.command.*;
import org.example.exception.CatalogException;
import org.example.model.Resource;
import org.example.repository.Catalog;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Initializare Catalog
            Catalog catalog = new Catalog("My References");

            Resource r1 = new Resource("knuth67", "The Art of Computer Programming", "d:/books/tacp.ps");
            Resource r2 = new Resource("jvm25", "The Java Virtual Machine Specification", "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html");

            // 2. Adaugare elemente folosind AddCommand
            new AddCommand(catalog, r1).execute();
            new AddCommand(catalog, r2).execute();

            // 3. Afisare folosind ListCommand
            System.out.println("--- LIST COMMAND ---");
            new ListCommand(catalog).execute();

            // 4. Salvare in fisier JSON folosind SaveCommand
            System.out.println("\n--- SAVE COMMAND ---");
            new SaveCommand(catalog, "catalog.json").execute();

            // 5. Incarcare din fisier JSON folosind LoadCommand
            System.out.println("\n--- LOAD COMMAND ---");
            LoadCommand loadCommand = new LoadCommand("catalog.json");
            loadCommand.execute();
            Catalog loadedCatalog = loadCommand.getLoadedCatalog();

            // 6. Generare raport HTML folosind ReportCommand (Se va deschide singur)
            System.out.println("\n--- REPORT COMMAND ---");
            new ReportCommand(loadedCatalog, "raport_catalog.html").execute();

            // 7. Deschidere fisier folosind ViewCommand (Link-ul Oracle va merge sigur)
            System.out.println("\n--- VIEW COMMAND ---");
            System.out.println("Incerc deschiderea resursei...");
            new ViewCommand(loadedCatalog.findById("jvm25")).execute();

        } catch (CatalogException e) {
            System.err.println("A aparut o eroare in executia unei comenzi: " + e.getMessage());
        }
    }
}