package it.giuugcola.OOPProject.JSONManage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {

    //da Oggetto a Json
    public static JSONObject toJSON(Object text) {
        JSONParser jParser = new JSONParser();
        JSONObject jObject_root = null;
        try {
            jObject_root = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jObject_root;
    }

    public static void populateClassJSONOfMultimedia(Object result) {
        JSONParser jParser = new JSONParser();

        try{
            JSONObject jObject_root = (JSONObject) jParser.parse(result.toString());
            JSONOfMultimedia.setPath_display((String) jObject_root.get("path_display"));
            JSONOfMultimedia.setRev((String) jObject_root.get("rev"));
            JSONOfMultimedia.setSize((Long) jObject_root.get("size"));
            JSONOfMultimedia.setServer_modified((String) jObject_root.get("server_modified"));
            JSONOfMultimedia.setPath_lower((String) jObject_root.get("path_lower"));
            JSONOfMultimedia.setIs_downloadable((Boolean) jObject_root.get("is_downloadable"));
            JSONOfMultimedia.setName((String) jObject_root.get("name"));
            JSONOfMultimedia.setTag((String) jObject_root.get(".tag"));
            JSONOfMultimedia.setId((String) jObject_root.get("id"));
            JSONOfMultimedia.setContent_hash((String) jObject_root.get("content_hash"));
            JSONOfMultimedia.setClient_modified((String) jObject_root.get("client_modified"));

            if( jObject_root.containsKey("media_info") ){
                JSONObject jObject_media_info = (JSONObject) jObject_root.get("media_info");

                if( jObject_media_info.containsKey("metadata") ){
                    JSONObject jObject_metadata = (JSONObject) jObject_media_info.get("metadata");
                    JSONOfMultimedia.setTag((String) jObject_metadata.get(".tag"));

                    if(jObject_metadata.containsKey("dimensions")){
                        JSONObject jObject_dimensions = (JSONObject) jObject_metadata.get("dimensions");
                        JSONOfMultimedia.setWidth((Long) jObject_dimensions.get("width"));
                        JSONOfMultimedia.setHeight((Long) jObject_dimensions.get("height"));
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void populateClassJSONOfFolder(Object result) {
        JSONParser jParser = new JSONParser();

        try{
            JSONObject jObject_root = (JSONObject) jParser.parse(result.toString());
            if( jObject_root.containsKey("metadata") ){
                JSONObject jObject_metadata = (JSONObject) jObject_root.get("metadata");
                JSONOfFolder.setPath_display((String) jObject_metadata.get("path_display"));
                JSONOfFolder.setPath_lower((String) jObject_metadata.get("path_lower"));
                JSONOfFolder.setName((String) jObject_metadata.get("name"));
                JSONOfFolder.setTag((String) jObject_metadata.get(".tag"));
                JSONOfFolder.setId((String) jObject_metadata.get("id"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
