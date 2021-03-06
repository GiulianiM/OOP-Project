package it.giuugcola.oop.restcontroller;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.oop.exceptions.*;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ControllerTest {

    Controller test = new Controller();

    ControllerTest() throws DropboxExceptions, FileException, ParsingToJsonException, DownloadException {
    }

    @DisplayName("Test getDataPathName")
    @ParameterizedTest
    @MethodSource("provideStringsForGetDataPathName")
    public void getDataPathName(String path, String json) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(path);
        Assertions.assertEquals(JSONHandler.objectToJson(json), JSONHandler.objectToJson(result));
    }

    private static Stream<Arguments> provideStringsForGetDataPathName() {
        return Stream.of(
                Arguments.of("/Documents", """
                        {
                            "path_display": "/Documents",
                            "path_lower": "/documents",
                            "name": "Documents",
                            ".tag": "folder",
                            "id": "id:DsnniHqkdCgAAAAAAAAACQ"
                        }"""),
                Arguments.of("/Images/Parrots.jpg", """
                        {
                            "path_display": "/Images/Parrots.jpg",
                            "rev": "5d2681c657bb288ff28e1",
                            "size": 36454,
                            "server_modified": "2021-12-05T15:51:18Z",
                            "path_lower": "/images/parrots.jpg",
                            "is_downloadable": true,
                            "name": "Parrots.jpg",
                            ".tag": "file",
                            "id": "id:DsnniHqkdCgAAAAAAAAABw",
                            "content_hash": "ae63e1a2e4b219e765921f0e5ee2fa32d3f87f55e1eed36b67688ddffc820d2c",
                            "client_modified": "2021-12-05T15:51:18Z"
                        }""")
        );
    }

    @DisplayName("Test getDataId")
    @ParameterizedTest
    @MethodSource("provideStringsForGetDataId")
    public void getDataId(String id, String json) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(id);
        Assertions.assertEquals(JSONHandler.objectToJson(json), JSONHandler.objectToJson(result));
    }

    private static Stream<Arguments> provideStringsForGetDataId() {
        return Stream.of(
                Arguments.of("id:DsnniHqkdCgAAAAAAAAACQ", """
                        {
                            "path_display": "/Documents",
                            "path_lower": "/documents",
                            "name": "Documents",
                            ".tag": "folder",
                            "id": "id:DsnniHqkdCgAAAAAAAAACQ"
                        }"""),
                Arguments.of("id:DsnniHqkdCgAAAAAAAAABw", """
                        {
                            "path_display": "/Images/Parrots.jpg",
                            "rev": "5d2681c657bb288ff28e1",
                            "size": 36454,
                            "server_modified": "2021-12-05T15:51:18Z",
                            "path_lower": "/images/parrots.jpg",
                            "is_downloadable": true,
                            "name": "Parrots.jpg",
                            ".tag": "file",
                            "id": "id:DsnniHqkdCgAAAAAAAAABw",
                            "content_hash": "ae63e1a2e4b219e765921f0e5ee2fa32d3f87f55e1eed36b67688ddffc820d2c",
                            "client_modified": "2021-12-05T15:51:18Z"
                        }""")
        );
    }

    @DisplayName("Test DownloadFile")
    @ParameterizedTest
    @MethodSource("provideStringsForDownloadFile")
    public void downloadFile(String path, String json) throws DownloadException, DropboxExceptions, ParsingToJsonException {
        FileMetadata result = CallsHandler.downloadFile(path);
        test.getDownloaded().addMultimedia(result);
        Assertions.assertFalse(JSONHandler.objectToJson(result).isEmpty());
        Assertions.assertEquals(JSONHandler.objectToJson(json), JSONHandler.objectToJson(result));
    }

    private static Stream<Arguments> provideStringsForDownloadFile() {
        return Stream.of(
                Arguments.of("/Documents/sample_of_text.txt", """
                        {
                            "path_display": "/Documents/sample_of_text.txt",
                            "rev": "5d2683f1866b388ff28e1",
                            "size": 2167737,
                            "server_modified": "2021-12-05T16:01:00Z",
                            "path_lower": "/documents/sample_of_text.txt",
                            "is_downloadable": true,
                            "name": "sample_of_text.txt",
                            ".tag": "file",
                            "id": "id:DsnniHqkdCgAAAAAAAAADA",
                            "content_hash": "f29056188266ade4d60fee7ed76cf2a3b4346ca4f4c7cd67562fb9f9cd82cecc",
                            "client_modified": "2021-12-05T16:01:00Z"
                        }"""),
                Arguments.of("/Images/Parrots.jpg", """
                        {
                            "path_display": "/Images/Parrots.jpg",
                            "rev": "5d2681c657bb288ff28e1",
                            "media_info": {
                                "metadata": {
                                    ".tag": "photo",
                                    "dimensions": {
                                        "width": 612,
                                        "height": 408
                                    }
                                },
                                ".tag": "metadata"
                            },
                            "size": 36454,
                            "server_modified": "2021-12-05T15:51:18Z",
                            "path_lower": "/images/parrots.jpg",
                            "is_downloadable": true,
                            "name": "Parrots.jpg",
                            ".tag": "file",
                            "id": "id:DsnniHqkdCgAAAAAAAAABw",
                            "content_hash": "ae63e1a2e4b219e765921f0e5ee2fa32d3f87f55e1eed36b67688ddffc820d2c",
                            "client_modified": "2021-12-05T15:51:18Z"
                        }""")
        );
    }

    @DisplayName("Test DownloadZip")
    @ParameterizedTest
    @MethodSource("provideStringsForDownloadZip")
    public void downloadZip(String path, String json) throws DownloadException, DropboxExceptions, ParsingToJsonException {
        DownloadZipResult result = CallsHandler.downloadZip(path);
        test.getDownloaded().addFolder(result);
        Assertions.assertFalse(JSONHandler.objectToJson(result).isEmpty());
        Assertions.assertEquals(JSONHandler.objectToJson(result), JSONHandler.objectToJson(json));
    }

    private static Stream<Arguments> provideStringsForDownloadZip() {
        return Stream.of(
                Arguments.of("/Images", """
                        {
                            "metadata": {
                                "path_display": "/Images",
                                "path_lower": "/images",
                                "name": "Images",
                                ".tag": "folder",
                                "id": "id:DsnniHqkdCgAAAAAAAAABg"
                            }
                        }"""),
                Arguments.of("/Documents", """
                        {
                            "metadata": {
                                "path_display": "/Documents",
                                "path_lower": "/documents",
                                "name": "Documents",
                                ".tag": "folder",
                                "id": "id:DsnniHqkdCgAAAAAAAAACQ"
                            }
                        }""")
        );
    }

}