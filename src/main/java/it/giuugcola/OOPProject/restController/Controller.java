package it.giuugcola.OOPProject.restController;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import it.giuugcola.OOPProject.metaData.DownloadedContent;
import it.giuugcola.OOPProject.settings.Constants;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller implements Constants {
    DownloadedContent AOM = new DownloadedContent();

    @RequestMapping("/")
    public String homePage() {
        return """
                <h1 style="text-align: center;"><strong> Progetto OOP </strong></h1>
                <p style="text-align: center;"><strong> Giuliani Matteo </strong></p>
                <p style="text-align: center;"><strong> Colabella Davide </strong></p>
                """;
    }

    @PostMapping("/dir")
    public Object dir() {
        CallsHandler call = new CallsHandler();
        ListFolderResult result = CallsHandler.list_folder_root();
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataId")
    public JSONObject getDataId(@RequestParam(name = "id") String id) {
        Metadata result = CallsHandler.get_metadata(id);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataPathName")
    public JSONObject getDataPathName(@RequestParam(name = "path") String pathName) {
        Metadata result = CallsHandler.get_metadata(pathName);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name="path") String path) {
        FileMetadata result = CallsHandler.download(path);
        AOM.addMultimedia(result);
        System.out.println(AOM.getMultimedia());

        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name="path") String path) {
        DownloadZipResult result = CallsHandler.download_zip(path);
        AOM.addFolder(result);
        System.out.println(AOM.getFolders());

        return JSONHandler.toJSON(result);
    }
}