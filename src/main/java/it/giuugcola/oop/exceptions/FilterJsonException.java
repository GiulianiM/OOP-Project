package it.giuugcola.oop.exceptions;

import java.io.Serial;

public class FilterJsonException extends Exception {
    @Serial
    private static final long serialVersionUID = 5L;

    public FilterJsonException(String message) {
        super(message);
    }
}
