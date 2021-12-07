package it.giuugcola.OOPProject.restController;

import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import it.giuugcola.OOPProject.settings.Constants;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller implements Constants {

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
        String result = call.list_folder_root();
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataId")
    public JSONObject getDataId(@RequestParam(name = "id") String id) {
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(id);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/getDataPathName")
    public JSONObject getDataPathName(@RequestParam(name = "path") String pathName) {
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(pathName);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadFile")
    public JSONObject downloadFile(@RequestParam(name="path") String path) {
        CallsHandler call = new CallsHandler();
        FileMetadata result = call.download(path);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadZip")
    public JSONObject downloadZip(@RequestParam(name="path") String path) {
        CallsHandler call = new CallsHandler();
        DownloadZipResult result = call.download_zip(path);
        return JSONHandler.toJSON(result);
    }
}