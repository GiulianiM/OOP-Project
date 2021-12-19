package it.giuugcola.oop.exceptions;

import java.io.Serial;

/**
 * Eccezione per errori nella creazione dei JSON.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class ParsingToJsonException extends Exception {
    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * Crea l'eccezione con il messaggio specificato.
     * @param message Il messaggio d'errore
     */
    public ParsingToJsonException(String message) {
        super(message);
    }
}
