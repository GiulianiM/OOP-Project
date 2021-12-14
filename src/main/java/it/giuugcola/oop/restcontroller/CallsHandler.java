package it.giuugcola.oop.restcontroller;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.oop.exceptions.DropboxExceptions;
import it.giuugcola.oop.exceptions.DownloadExcepton;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.settings.DropboxClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class CallsHandler {

    private static final String DOWNLOAD_FOLDER_PATH = "downloads/";

    public static ListFolderResult listFolder(String path) throws DropboxExceptions {
        ListFolderResult metadata;

        try {
            metadata = DropboxClient.client
                    .files()
                    .listFolder(path);
        } catch (DbxException e) {
            throw new DropboxExceptions("Parametro 'path' incorretto");
        }

        return metadata;
    }

    public static Metadata getMetadata(String path) throws DropboxExceptions {
        Metadata metadata;

        try {
            metadata = DropboxClient.client
                    .files()
                    .getMetadata(path);
        } catch (DbxException e) {
            throw new DropboxExceptions("Parametro 'path' incorretto!");
        }

        return metadata;
    }

    public static FileMetadata downloadFile(String path) throws DropboxExceptions, DownloadExcepton, ParsingToJsonException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH + getFileOrFolderName(path);
        FileMetadata metadata = null;

        if (isPathExist(path)) {
            try (OutputStream outputStream = new FileOutputStream(localDownloadPath)){
                metadata = DropboxClient.client
                        .files()
                        .downloadBuilder(path)
                        .download(outputStream);
            } catch (DbxException e) {
                throw new DropboxExceptions("Parametro 'path' incorretto! il metadata da scaricare è inesistente");
            } catch (IOException e) {
                throw new DownloadExcepton("Cartella dei download non trovata");
            }
        }

        return metadata;
    }

    public static DownloadZipResult downloadZip(String path) throws DropboxExceptions, DownloadExcepton, ParsingToJsonException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH +  getFileOrFolderName(path) + ".zip";
        DownloadZipResult metadata = null;

       if (isPathExist(path)) {
            try (OutputStream outputStream = new FileOutputStream(localDownloadPath)){
                metadata = DropboxClient.client
                        .files()
                        .downloadZipBuilder(path)
                        .download(outputStream);
            } catch (DbxException e) {
                throw new DropboxExceptions("Parametro 'path' incorretto! Probabile cartella inesistente");
            } catch (IOException e) {
                throw new DownloadExcepton("Cartella dei download non trovata");
            }
       }
        return metadata;
    }


    private static boolean isPathExist(String path) throws DropboxExceptions, ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        Metadata metadata = getMetadata(path);
        try {
            JSONObject jObject = (JSONObject) jParser.parse(metadata.toString());
            if (jObject.get("path_lower").equals(path.toLowerCase())) {
                return true;
            }
        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parsing!");
        }
        return false;
    }
    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private static String getFileOrFolderName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
