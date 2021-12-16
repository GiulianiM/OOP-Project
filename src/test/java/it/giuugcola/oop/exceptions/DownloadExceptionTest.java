package it.giuugcola.oop.exceptions;

import it.giuugcola.oop.restcontroller.CallsHandler;
import it.giuugcola.oop.restcontroller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DownloadExceptionTest {

    @DisplayName("Test DownloadException")
    @ParameterizedTest
    @ValueSource(strings = {"/Images/parrots.jpg"})
    public void whenExceptionThrown_thenAssertionSucceeds(String path) throws ParsingToJsonException, FileException, DropboxExceptions {
        Controller test = new Controller();
        Exception exception = assertThrows(DownloadException.class, () -> {
            CallsHandler.downloadZip(path);
        });

        String expectedMessage = "Impossibile scaricare questo file come zip!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}