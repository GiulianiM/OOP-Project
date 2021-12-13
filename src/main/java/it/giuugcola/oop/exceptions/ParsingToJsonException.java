package it.giuugcola.oop.exceptions;

public class ParsingToJsonException extends Exception {
    private static final long serialVersionUID = 3L;

    public ParsingToJsonException(String message) {
        super(message);
    }
}
