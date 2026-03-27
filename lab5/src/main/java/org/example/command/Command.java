package org.example.command;

import org.example.exception.CatalogException;

/**
 * Interfata generica pentru toate comenzile aplicatiei noastre.
 */
public interface Command {
    void execute() throws CatalogException;
}