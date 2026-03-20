package org.example.main;

import org.example.exception.CatalogException;
import org.example.model.Resource;
import org.example.repository.Catalog;

/**
 * Clasa principala care testeaza functionalitatile Compulsory pentru Lab 5.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Cream obiectul care reprezinta repository-ul
        Catalog catalog = new Catalog("My References");

        // 2. Adaugam resurse in catalog
        Resource r1 = new Resource("knuth67", "The Art of Computer Programming", "d:/books/programming/tacp.ps");
        r1.addProperty("year", "1967");
        r1.addProperty("author", "Donald E. Knuth");

        Resource r2 = new Resource("jvm25", "The Java Virtual Machine Specification", "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html");
        r2.addProperty("year", "2025");
        r2.addProperty("author", "Tim Lindholm & others");

        Resource r3 = new Resource("java25", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf");
        r3.addProperty("year", "2025");
        r3.addProperty("author", "James Gosling & others");

        catalog.add(r1);
        catalog.add(r2);
        catalog.add(r3);

        System.out.println("Am incarcat " + catalog.findById("jvm25").getTitle() + " in catalog.");

        // 3. Deschidem o resursa folosind clasa Desktop (cu Exception Handling cerut)
        try {
            System.out.println("Incerc sa deschid link-ul JVM Specification in browser...");
            // link valid
            catalog.open(r2);
            System.out.println("Resursa deschisa cu succes!");

            // link invalid
            catalog.open(r1);

        } catch (CatalogException e) {
            // Aici logam in mod corect exceptia noastra custom
            System.err.println("Eroare in operatiunea de deschidere: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cauza tehnica a fost: " + e.getCause().getMessage());
            }
        }
    }
}