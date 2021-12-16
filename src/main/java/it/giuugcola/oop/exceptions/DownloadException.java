package it.giuugcola.oop.exceptions;

import java.io.Serial;

/**
 * Eccezione per errori di download.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class DownloadException extends Exception{
    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * Crea l'eccezione con il messaggio specificato.
     * @param message Il messaggio d'errore
     */
    public DownloadException(String message) {
        super(message);
    }
}
