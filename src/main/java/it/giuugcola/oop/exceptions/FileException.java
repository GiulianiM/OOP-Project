package it.giuugcola.oop.exceptions;

import java.io.Serial;

public class FileException extends Exception{
    @Serial
    private static final long serialVersionUID = 6L;

    public FileException(String message){
        super(message);
    }
}
