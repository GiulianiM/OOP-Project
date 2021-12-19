package it.giuugcola.oop.jsonhandler;

import com.dropbox.core.v2.files.FileMetadata;
import it.giuugcola.oop.exceptions.DownloadException;
import it.giuugcola.oop.exceptions.DropboxExceptions;
import it.giuugcola.oop.exceptions.FileException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.restcontroller.CallsHandler;
import it.giuugcola.oop.restcontroller.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JSONHandlerTest {

    @DisplayName("Test object to JSON")
    @ParameterizedTest
    @MethodSource("provideStringsForObjectToJSON")
    void objectToJson(String path, String json) throws ParsingToJsonException, DownloadException, DropboxExceptions, FileException {
        Controller prova = new Controller();
        FileMetadata result = CallsHandler.downloadFile(path);
        Assertions.assertEquals(JSONHandler.objectToJson(json), JSONHandler.objectToJson(result));
    }

    private static Stream<Arguments> provideStringsForObjectToJSON() {
        return Stream.of(
                Arguments.of("/Images/Parrots.jpg", """
                        {
                        	"path_display":"/Images/Parrots.jpg",
                        	"rev":"5d2681c657bb288ff28e1",
                        	"media_info": {
                        		"metadata": {
                        			".tag":"photo",
                        			"dimensions": {
                        				"width":612,
                        				"height":408
                        			}
                        		},
                        	".tag":"metadata"},
                        	"size":36454,
                        	"server_modified":"2021-12-05T15:51:18Z",
                        	"path_lower":"/images/parrots.jpg",
                        	"is_downloadable":true,
                        	"name":"Parrots.jpg",
                        	".tag":"file",
                        	"id":"id:DsnniHqkdCgAAAAAAAAABw",
                        	"content_hash":"ae63e1a2e4b219e765921f0e5ee2fa32d3f87f55e1eed36b67688ddffc820d2c",
                        	"client_modified":"2021-12-05T15:51:18Z"
                        }
                        """)
        );
    }
}