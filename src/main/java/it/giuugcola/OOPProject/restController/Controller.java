package it.giuugcola.OOPProject.restController;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import it.giuugcola.OOPProject.metaData.DownloadedContent;
import it.giuugcola.OOPProject.metaData.FileMap;
import it.giuugcola.OOPProject.metaData.FileMinAvgMax;
import it.giuugcola.OOPProject.utility.Html;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    DownloadedContent AOM = new DownloadedContent();

    public DownloadedContent getAOM() {
        return AOM;
    }

    @RequestMapping("/")
    public String homePage() {
        return Html.homePage();
    }

    @PostMapping("/dir")
    public JSONObject dir() {
        ListFolderResult result = CallsHandler.list_folder_root();
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataId")
    public JSONObject getDataId(@RequestParam(name = "id") String id) {
        Metadata result = CallsHandler.get_metadata(id);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataPathName")
    public JSONObject getDataPathName(@RequestParam(name = "path") String path) {
        Metadata result = CallsHandler.get_metadata(path);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name = "path") String path) {
        FileMetadata result = CallsHandler.download(path);
        AOM.addMultimedia(result);
        System.out.println(AOM.getMultimedia());

        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name = "path") String path) {
        DownloadZipResult result = CallsHandler.download_zip(path);
        AOM.addFolder(result);
        System.out.println(AOM.getFolders());

        return JSONHandler.toJSON(result);
    }

    @GetMapping("/stats")
    public JSONObject stats() {
        return JSONHandler.toJSONStats(new FileMap().populateMaps(AOM));
    }

    @GetMapping("/minAvgMax")
    public JSONObject statsmmm() {
        FileMinAvgMax fileMinAvgMax = CallsHandler.statsMAM(new FileMap().populateMaps(AOM)); //{} deriva da populateMaps
        return JSONHandler.toJSONMinAvgMax(fileMinAvgMax);
    }

    @GetMapping("/statsFiltered")
    public JSONObject statsFiltered(@RequestParam(name = "filter", defaultValue = "") String filter) {
        return JSONHandler.toJSONStats(new FileMap().populateMaps(CallsHandler.statsFiltered(AOM, filter)));
    }
}