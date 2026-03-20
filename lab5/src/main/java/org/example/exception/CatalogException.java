package org.example.exception;

/**
 * Exceptie personalizata pentru a semnala erori legate de catalogul bibliografic.
 * O folosim cand nu putem deschide o resursa sau cand datele sunt invalide.
 */
public class CatalogException extends Exception {

    /**
     * Constructor care primeste doar un mesaj de eroare.
     * @param message Mesajul detaliat al erorii
     */
    public CatalogException(String message) {
        super(message);
    }

    /**
     * Constructor care "inlantuieste" exceptiile (Chained Exceptions).
     * Foarte util pentru a pastra stack trace-ul original (ex: IOException).
     * @param message Mesajul detaliat al erorii
     * @param cause Exceptia originala care a cauzat problema
     */
    public CatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}