package it.giuugcola.oop.restcontroller;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.oop.exceptions.DropboxExceptions;
import it.giuugcola.oop.exceptions.DownloadException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.settings.DropboxClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Classe per la gestione di tutte le chiamate del controller
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class CallsHandler {

    /**
     * Percorso in cui vengono scaricati i file.
     */
    private static final String DOWNLOAD_FOLDER_PATH = "downloads/";

    /**
     * Metodo per ottenere il contenuto di un percorso, che si tratti di file o cartelle.
     *
     * @param path Percorso che si vuole visualizzare
     * @return Oggetto ListFolderResult contenente tutti i file/cartelle all'interno del percorso.
     * @throws DropboxExceptions Se il percorso non è corretto.
     */
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

    /**
     * Metodo per ottenere informazioni riguardo un file o una cartella specificando il percorso.
     *
     * @param path Percorso che si vuole visualizzare
     * @return Oggetto {@link Metadata} contenente tutte le informazioni.
     * @throws DropboxExceptions Se il percorso è errato.
     */
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

    /**
     * Metodo per scaricare un file.
     *
     * @param path Percorso del file.
     * @return Oggetto {@link FileMetadata} corrispondente al file da scaricare.
     * @throws DropboxExceptions Se il percorso è errato.
     * @throws DownloadException Se il file non può essere scaricato.
     */
    public static FileMetadata downloadFile(String path) throws DropboxExceptions, DownloadException, ParsingToJsonException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH + getFileOrFolderName(path);
        FileMetadata metadata;

        if (isPathExist(path)) {
            try (OutputStream outputStream = new FileOutputStream(localDownloadPath)) {
                metadata = DropboxClient.client
                        .files()
                        .downloadBuilder(path)
                        .download(outputStream);
            } catch (DbxException e) {
                throw new DropboxExceptions("Parametro 'path' incorretto! il metadata da scaricare è inesistente");
            } catch (IOException e) {
                throw new DownloadException("Cartella dei download non trovata");
            }
        } else
            throw new DownloadException("Impossibile scaricare questo file!");

        return metadata;
    }

    /**
     * Percorso per scaricare cartelle intere come zip.
     *
     * @param path Percorso della cartella.
     * @return Oggetto {@link DownloadZipResult}
     * @throws DropboxExceptions Se il percorso è errato.
     * @throws DownloadException Se la cartella non può essere scaricata.
     */
    public static DownloadZipResult downloadZip(String path) throws DropboxExceptions, DownloadException, ParsingToJsonException {
        String localDownloadPath = DOWNLOAD_FOLDER_PATH + getFileOrFolderName(path) + ".zip";
        DownloadZipResult metadata;

        if (isPathExist(path) && isPathDownloadableAsZip(path)) {
            try (OutputStream outputStream = new FileOutputStream(localDownloadPath)) {
                metadata = DropboxClient.client
                        .files()
                        .downloadZipBuilder(path)
                        .download(outputStream);
            } catch (DbxException e) {
                throw new DropboxExceptions("Parametro 'path' incorretto! Probabile cartella inesistente");
            } catch (IOException e) {
                throw new DownloadException("Cartella dei download non trovata");
            }
        } else
            throw new DownloadException("Impossibile scaricare questo file come zip!");
        return metadata;
    }

    /**
     * Metodo ausiliario di {@link #downloadZip(String)} per controllare se la cartella può essere scaricata come file .zip.
     *
     * @param path Percorso da controllare.
     * @return Esito del controllo.
     */
    private static boolean isPathDownloadableAsZip(String path) {
        String d = getFileOrFolderName(path);
        return !d.contains(".");
    }

    /**
     * Metodo ausiliario di {@link #downloadFile(String)} e {@link #downloadZip(String)} per controllare se il percorso esiste.
     *
     * @param path Percorso da controllare.
     * @return Esito del controllo.
     * @throws ParsingToJsonException Se il parsing to JSON da errore.
     */
    private static boolean isPathExist(String path) throws DropboxExceptions, ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        Metadata metadata = getMetadata(path);
        try {
            JSONObject jObject = (JSONObject) jParser.parse(metadata.toString());
            if (jObject.get("path_lower").equals(path.toLowerCase()))
                return true;
        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parsing!");
        }
        return false;
    }

    /**
     * Metodo ausiliario per ottenere il nome del file dal percorso, troncando dall'ultimo "/".
     *
     * @param path Percorso.
     * @return Nome file.
     */
    private static String getFileOrFolderName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
