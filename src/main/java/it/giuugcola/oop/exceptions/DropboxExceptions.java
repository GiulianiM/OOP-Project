package it.giuugcola.oop.exceptions;

import java.io.Serial;

public class DropboxExceptions extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public DropboxExceptions(String message) {
        super(message);
    }
}
