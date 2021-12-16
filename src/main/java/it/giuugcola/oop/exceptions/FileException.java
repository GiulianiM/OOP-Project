package it.giuugcola.oop.exceptions;

import java.io.Serial;

/**
 * Eccezione per errori riguardo alla scrittura e lettura su file.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class FileException extends Exception{
    @Serial
    private static final long serialVersionUID = 6L;

    /**
     * Crea l'eccezione con il messaggio specificato.
     * @param message Il messaggio d'errore
     */
    public FileException(String message){
        super(message);
    }
}
