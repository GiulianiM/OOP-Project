package it.giuugcola.oop.exceptions;

import java.io.Serial;

/**
 * Eccezione per errori di richiesta all'API di Dropbox.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class DropboxExceptions extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Crea l'eccezione con il messaggio specificato.
     * @param message Il messaggio d'errore
     */
    public DropboxExceptions(String message) {
        super(message);
    }
}
