package it.giuugcola.oop.exceptions;

import com.dropbox.core.DbxException;

public class DropboxExceptions extends Exception {
    private static final long serialVersionUID = 1L;

    public DropboxExceptions(String message) {
        super(message);
    }
}
