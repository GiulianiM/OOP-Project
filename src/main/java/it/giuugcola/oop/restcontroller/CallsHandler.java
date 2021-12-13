package it.giuugcola.oop.restcontroller;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.oop.exceptions.DropboxExceptions;
import it.giuugcola.oop.exceptions.OutputStreamException;
import it.giuugcola.oop.settings.DropboxClient;

import java.io.*;

public class CallsHandler {

    private static final String DOWNLOAD_FOLDER_PATH = "Downloads";

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
            throw new DropboxExceptions("Parametro 'path' incorretto");
        }

        return metadata;
    }

    public static FileMetadata downloadFile(String path) throws DropboxExceptions, OutputStreamException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH + "/" + getFileOrFolderName(path);

        FileMetadata metadata;
        try {
            OutputStream outputStream = new FileOutputStream(localDownloadPath);
            metadata = DropboxClient.client
                    .files()
                    .downloadBuilder(path)
                    .download(outputStream);
            outputStream.close();
        } catch (DbxException e) {
            throw new DropboxExceptions("Parametro 'path' incorretto");
        }catch (IOException e){
            throw new OutputStreamException("Cartella dei download non trovata");
        }

        //Todo restituire una stringa nuccun chiù bell
        return metadata;
    }

    public static DownloadZipResult downloadZip(String path) throws DropboxExceptions, OutputStreamException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH + "/" + getFileOrFolderName(path) + ".zip";

        DownloadZipResult metadata ;
        try {
            OutputStream outputStream = new FileOutputStream(localDownloadPath);
            metadata = DropboxClient.client
                    .files()
                    .downloadZipBuilder(path)
                    .download(outputStream);
            outputStream.close();
        } catch (DbxException e) {
            throw new DropboxExceptions("Parametro 'path' incorretto");
        }catch (IOException e){
            throw new OutputStreamException("Cartella dei download non trovata");
        }

        return metadata;
    }

    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private static String getFileOrFolderName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
