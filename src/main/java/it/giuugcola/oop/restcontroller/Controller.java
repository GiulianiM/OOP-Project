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

@RestController
public class Controller {

    private Downloaded downloaded = new Downloaded();
    @SuppressWarnings("unused")
    private InputFromFile inFromFile = new InputFromFile(downloaded);
    @SuppressWarnings("unused")
    private DropboxClient client = new DropboxClient();
    private OutputOnFile outOnFile = new OutputOnFile();

    public Controller() throws DropboxExceptions, FileException, ParsingToJsonException {
        // TODO document why this constructor is empty
    }

    @RequestMapping("/")
    public String homePage() {
        return Html.homePage();
    }

    @PostMapping("/dirRoot")
    public JSONObject dirRoot() throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder("");
        return JSONHandler.objectToJson(result);
    }

    @PostMapping("/dir")
    public JSONObject dir(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        ListFolderResult result = CallsHandler.listFolder(path);
        return JSONHandler.objectToJson(result);
    }

    @PostMapping("/getMetaFromId")
    public JSONObject getMetaFromId(@RequestParam(name = "id") String id) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(id);
        return JSONHandler.objectToJson(result);
    }

    @PostMapping("/getMetaFromPath")
    public JSONObject getMetaFromPath(@RequestParam(name = "path") String path) throws DropboxExceptions, ParsingToJsonException {
        Metadata result = CallsHandler.getMetadata(path);
        return JSONHandler.objectToJson(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name = "path") String path) throws DownloadExcepton, DropboxExceptions, ParsingToJsonException, FileException {
        FileMetadata result = CallsHandler.downloadFile(path);
        if (downloaded.addMultimedia(result))
            outOnFile.writeMultimedia(result);
        return JSONHandler.objectToJson(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name = "path") String path) throws DownloadExcepton, DropboxExceptions, ParsingToJsonException, FileException {
        DownloadZipResult result = CallsHandler.downloadZip(path);
        if (downloaded.addFolder(result))
            outOnFile.writeFolder(result);
        return JSONHandler.objectToJson(result);
    }

    @GetMapping("/stats")
    public JSONObject stats(@RequestParam(name = "filter", defaultValue = "") String filter, @RequestParam(name = "tag", defaultValue = "") String tag) throws FilterJsonException, FileException {
        JSONObject filteredToJson = JSONHandler.filteredToJson(downloaded, filter, tag);
        outOnFile.writeFiltered(filteredToJson);
        return filteredToJson;
    }

    @GetMapping("/stats/getMinAvgMax")
    public JSONObject statsMinAvgMax(@RequestParam(name = "tag", defaultValue = "") String tag) throws FileException {
        JSONObject minAvgMaxToJson = JSONHandler.minAvgMaxToJson(downloaded, tag);
        outOnFile.writeMinAvgMax(minAvgMaxToJson);
        return minAvgMaxToJson;
    }

    //per i test
    public Downloaded getDownloaded() {
        return downloaded;
    }
}