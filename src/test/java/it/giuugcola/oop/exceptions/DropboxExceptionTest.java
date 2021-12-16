package it.giuugcola.oop.exceptions;

import it.giuugcola.oop.restcontroller.CallsHandler;
import it.giuugcola.oop.restcontroller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DropboxExceptionTest {

    @DisplayName("Test DropboxException")
    @ParameterizedTest
    @ValueSource(strings = {"/Prova", "/ciao"})
    public void whenExceptionThrown_thenAssertionSucceeds(String path) throws ParsingToJsonException, FileException, DropboxExceptions {
        Controller test = new Controller();
        Exception exception = assertThrows(DropboxExceptions.class, () -> {
            CallsHandler.downloadFile(path);
        });

        String expectedMessage = "Parametro 'path' incorretto!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}