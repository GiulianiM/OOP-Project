package it.giuugcola.oop.restcontroller;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.metadata.Downloaded;
import it.giuugcola.oop.metadata.FileMap;
import it.giuugcola.oop.metadata.FileMinAvgMax;
import it.giuugcola.oop.utility.Html;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private Downloaded downloaded = new Downloaded();

    public Downloaded getDownloaded() {
        return downloaded;
    }

    @RequestMapping("/")
    public String homePage() {
        return Html.homePage();
    }

    @PostMapping("/dir")
    public JSONObject dir() {
        ListFolderResult result = CallsHandler.listFolderRoot();
        return JSONHandler.toJson(result);
    }

    @PostMapping("/getDataId")
    public JSONObject getDataId(@RequestParam(name = "id") String id) {
        Metadata result = CallsHandler.getMetadata(id);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/getDataPathName")
    public JSONObject getDataPathName(@RequestParam(name = "path") String path) {
        Metadata result = CallsHandler.getMetadata(path);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name = "path") String path) {
        FileMetadata result = CallsHandler.downloadFile(path);
        downloaded.addMultimedia(result);
        return JSONHandler.toJson(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name = "path") String path) {
        DownloadZipResult result = CallsHandler.downloadZip(path);
        downloaded.addFolder(result);
        return JSONHandler.toJson(result);
    }

    @GetMapping("/getList")
    public JSONObject getList() {
        return JSONHandler.toJsonStats(new FileMap().populateMaps(downloaded));
    }

    @GetMapping("/getListMinAvgMax")
    public JSONObject getListMinAvgMax() {
        FileMinAvgMax fileMinAvgMax = CallsHandler.getMinAvgMax(new FileMap().populateMaps(downloaded)); //{} deriva da populateMaps
        return JSONHandler.toJsonMinAvgMax(fileMinAvgMax);
    }

    @GetMapping("/getListFiltered")
    public JSONObject getListFiltered(@RequestParam(name = "filter") String filter) {
        return JSONHandler.toJsonStats(new FileMap().populateMaps(CallsHandler.getFiltered(downloaded, filter)));
    }
}