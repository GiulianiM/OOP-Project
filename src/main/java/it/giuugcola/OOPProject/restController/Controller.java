package it.giuugcola.OOPProject.restController;

import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import org.springframework.web.bind.annotation.*;
import it.giuugcola.OOPProject.settings.Contants;

@RestController
public class Controller implements Contants{

    @RequestMapping("/")
    public String homePage(){
        return """
                <h1 style="text-align: center;"><strong>Progetto OOP</strong></h1>
                <p style="text-align: center;"><strong> Giuliani Matteo</strong></p>
                <p style="text-align: center;"><strong>Colabella Davide</strong></p>
                """;
    }

    //restituisce una lista di ciò che è presente nella cartella di root
    @PostMapping("/dir")
    public Object dir(){
        CallsHandler call = new CallsHandler();
        String result = call.list_folder_root();
        return JSONHandler.toJSON(result);
    }

    //restituisce metadati relativi a file o directory tramite ID
    //es: "id:DsnniHqkdCgAAAAAAAAABg"
    @PostMapping("/getDataId")
    public Object getDataId(@RequestParam(name="id")String id){
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(id);
        return JSONHandler.toJSON(result);
    }

    //restituisce metadati relativi a file o directory tramite PATHNAME
    //es: "/Videos"
    @PostMapping("/getDataPathName")
    public Object getDataPathName(@RequestParam(name="pathName")String pathName){
        CallsHandler call = new CallsHandler();
        String result = call.get_metadata(pathName);
        return JSONHandler.toJSON(result);
    }
}
