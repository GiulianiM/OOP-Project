package it.giuugcola.oop.exceptions;

import java.io.Serial;

public class ParsingToJsonException extends Exception {
    @Serial
    private static final long serialVersionUID = 3L;

    public ParsingToJsonException(String message) {
        super(message);
    }
}
