package it.giuugcola.oop.exceptions;

import java.io.Serial;

/**
 * Eccezione per errori riguardo al filter dato in input per la creazione del JSON.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class FilterJsonException extends Exception {
    @Serial
    private static final long serialVersionUID = 5L;

    /**
     * Crea l'eccezione con il messaggio specificato.
     * @param message Il messaggio d'errore
     */
    public FilterJsonException(String message) {
        super(message);
    }
}
