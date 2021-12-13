package it.giuugcola.oop.restcontroller;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.oop.exceptions.DropboxExceptions;
import it.giuugcola.oop.exceptions.FilterJsonException;
import it.giuugcola.oop.exceptions.OutputStreamException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.metadata.Downloaded;
import it.giuugcola.oop.settings.DropboxClient;
import it.giuugcola.oop.utility.Html;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private Downloaded downloaded = new Downloaded();
    @SuppressWarnings("unused")
    private DropboxClient client = new DropboxClient();

    public Controller() throws DropboxExceptions {
    }

    @RequestMapping("/")
    public String homePage() {
        return Html.homePage();
    }

    @PostMapping("/dirRoot")
    public JSONObject dirRoot() throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder("");
        return JSONHandler.toJson(result);
    }

    @PostMapping("/dir")
    public JSONObject dir(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder(path);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/getMetaFromId")
    public JSONObject getMetaFromId(@RequestParam(name = "id") String id) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(id);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/getMetaFromPath")
    public JSONObject getMetaFromPath(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(path);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name = "path") String path) throws OutputStreamException, DropboxExceptions, ParsingToJsonException {
        FileMetadata result = CallsHandler.downloadFile(path);
        downloaded.addMultimedia(result);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name = "path") String path) throws OutputStreamException, DropboxExceptions, ParsingToJsonException  {
        DownloadZipResult result = CallsHandler.downloadZip(path);
        downloaded.addFolder(result);
        return JSONHandler.toJson(result);
    }

    //todo due parametri "type" e "filter" e unire con getFiltered
    @GetMapping("/stats")
    public JSONObject stats(@RequestParam(name = "filter", defaultValue = "") String filter, @RequestParam(name = "tag", defaultValue = "") String tag) throws FilterJsonException {
        return JSONHandler.toJsonFiltered(downloaded, filter, tag);
    }

    @GetMapping("/stats/getMinAvgMax")
    public JSONObject statsMinAvgMax(@RequestParam(name = "tag", defaultValue = "") String tag) {
        return JSONHandler.toJsonMinAvgMax(downloaded, tag);
    }

    //per i test
    public Downloaded getDownloaded() {
        return downloaded;
    }
}