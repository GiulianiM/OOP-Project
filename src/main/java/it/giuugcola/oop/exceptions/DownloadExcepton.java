package it.giuugcola.oop.exceptions;

import java.io.Serial;

public class DownloadExcepton extends Exception{
    @Serial
    private static final long serialVersionUID = 2L;

    public DownloadExcepton(String message) {
        super(message);
    }
}
