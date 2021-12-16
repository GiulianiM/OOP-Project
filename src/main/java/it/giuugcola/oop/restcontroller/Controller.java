package it.giuugcola.oop.restcontroller;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.oop.exceptions.*;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.metadata.Downloaded;
import it.giuugcola.oop.settings.DropboxClient;
import it.giuugcola.oop.stream.InputFromFile;
import it.giuugcola.oop.stream.OutputOnFile;
import it.giuugcola.oop.utility.Html;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * Classe Controller per la gestione di tutte le chiamate dell'applicazione.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
@RestController
public class Controller {

    /**
     * Oggetto {@link Downloaded} contenente tutti i file e le cartelle scaricati.
     */
    private Downloaded downloaded = new Downloaded();
    /**
     * Oggetto {@link InputFromFile} per leggere da file esterni i download effettuati.
     */
    @SuppressWarnings("unused")
    private InputFromFile inFromFile = new InputFromFile(downloaded);
    /**
     * Oggetto {@link DropboxClient} creato per effettuare la connessione all'API di Dropbox.
     */
    @SuppressWarnings("unused")
    private DropboxClient client = new DropboxClient();
    /**
     * Oggetto {@link OutputOnFile} per scrivere su file esterni i download effettuati.
     */
    private OutputOnFile outOnFile = new OutputOnFile();

    public Controller() throws DropboxExceptions, FileException, ParsingToJsonException {
        // TODO document why this constructor is empty
    }

    /**
     * Rotta della home page dell'applicazione.
     *
     * @return La homepage del programma.
     */
    @RequestMapping("/")
    public String homePage() {
        return Html.homePage();
    }

    /**
     * Chiamata <b>POST</b> per ottenere tutti i file/cartelle presenti nella root di Dropbox.
     *
     * @return JSON del contenuto della root.
     */
    @PostMapping("/dirRoot")
    public JSONObject dirRoot() throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder("");
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>POST</b> per ottenere tutti i file/cartelle presenti nel percorso specificato di Dropbox.
     *
     * @param path Percorso
     * @return JSON del contenuto del percorso.
     */
    @PostMapping("/dir")
    public JSONObject dir(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder(path);
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>POST</b> per ottenere informazioni su file/cartelle con il loro ID.
     *
     * @param id Id del file/cartella.
     * @return JSON delle caratteristiche del file/cartella.
     */
    @PostMapping("/getMetaFromId")
    public JSONObject getMetaFromId(@RequestParam(name = "id") String id) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(id);
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>POST</b> per ottenere informazioni su file/cartelle con il loro percorso.
     *
     * @param path percorso del file/cartella.
     * @return JSON delle caratteristiche del file/cartella.
     */
    @PostMapping("/getMetaFromPath")
    public JSONObject getMetaFromPath(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(path);
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>POST</b> per scaricare un file con il suo percorso. Il file viene scaricato nella cartella /downloads.
     *
     * @param path Percorso del file
     * @return JSON contenente le caratteristiche del file.
     */
    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name = "path") String path) throws DownloadException, DropboxExceptions, ParsingToJsonException, FileException {
        FileMetadata result = CallsHandler.downloadFile(path);
        if (downloaded.addMultimedia(result))
            outOnFile.writeMultimedia(result);
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>POST</b> per scaricare una cartella con il suo percorso. La cartella viene scaricata nella cartella /downloads.
     *
     * @param path Percorso della cartella.
     * @return JSON contenente le caratteristiche della cartella.
     */
    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name = "path") String path) throws DownloadException, DropboxExceptions, ParsingToJsonException, FileException {
        DownloadZipResult result = CallsHandler.downloadZip(path);
        if (downloaded.addFolder(result))
            outOnFile.writeFolder(result);
        return JSONHandler.objectToJson(result);
    }

    /**
     * Chiamata <b>GET</b> per ottenere la lista dei file scaricati,
     * con la possibilità di filtrarli per dimensione e tipo.
     *
     * @param filter Filtro che si vuole utilizzare, di default è vuoto.
     * @param tag    Tipo di file che si vuole visualizzare, di default è vuoto.
     * @return JSON della lista dei file scaricati filtrata o meno.
     */
    @GetMapping("/stats")
    public JSONObject stats(@RequestParam(name = "filter", defaultValue = "") String filter, @RequestParam(name = "tag", defaultValue = "") String tag) throws FilterJsonException, FileException {
        JSONObject filteredToJson = JSONHandler.filteredToJson(downloaded, filter, tag);
        outOnFile.writeFiltered(filteredToJson);
        return filteredToJson;
    }

    /**
     * Chiamata <b>GET</b> per ottenere la lista divisa per tipo del file più piccolo, più grande e della dimensione media.
     *
     * @param tag Tipo di file che si vuole visualizzare, di default è vuoto.
     * @return JSON della lista dei file scaricati divisa per tipo, contenente il file
     * più piccolo, più grande e la dimensione media.
     */
    @GetMapping("/stats/getMinAvgMax")
    public JSONObject statsMinAvgMax(@RequestParam(name = "tag", defaultValue = "") String tag) throws FileException {
        JSONObject minAvgMaxToJson = JSONHandler.minAvgMaxToJson(downloaded, tag);
        outOnFile.writeMinAvgMax(minAvgMaxToJson);
        return minAvgMaxToJson;
    }

    /**
     * Getter della lista di file scaricati
     *
     * @return Oggetto {@link Downloaded}
     */
    public Downloaded getDownloaded() {
        return downloaded;
    }
}