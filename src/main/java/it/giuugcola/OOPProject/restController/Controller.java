package it.giuugcola.OOPProject.restController;

import com.dropbox.core.v2.files.FileMetadata;
import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import it.giuugcola.OOPProject.settings.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller implements Constants {

    @RequestMapping("/")
    public String homePage() {
        return """
                <h1 style="text-align: center;"><strong>Progetto OOP</strong></h1>
                <p style="text-align: center;"><strong> Giuliani Matteo</strong></p>
                <p style="text-align: center;"><strong>Colabella Davide</strong></p>
                """;
    }

    //restituisce una lista di ciò che è presente nella cartella di root
    @PostMapping("/dir")
    public Object dir() {
        CallsHandler call = new CallsHandler();
        String result = call.list_folder_root();
        return JSONHandler.toJSON(result);
    }

    //restituisce metadati relativi a file o directory tramite ID
    //es: "id:DsnniHqkdCgAAAAAAAAABg"
    @PostMapping("/getDataId")
    public Object getDataId(@RequestParam(name = "id") String id) {
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(id);
        return JSONHandler.toJSON(result);
    }

    //restituisce metadati relativi a file o directory tramite PATHNAME
    //es: "/Videos"
    @PostMapping("/getDataPathName")
    public Object getDataPathName(@RequestParam(name = "pathName") String pathName) {
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(pathName);
        return JSONHandler.toJSON(result);
    }

    @PostMapping("/downloadFile")
    public FileMetadata downloadFile(@RequestParam(name="path") String path, @RequestParam(name = "fileName") String fileName) {
        CallsHandler call = new CallsHandler();
        return call.download(path, fileName);
    }
}