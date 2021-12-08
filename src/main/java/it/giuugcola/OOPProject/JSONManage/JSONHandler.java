package it.giuugcola.OOPProject.JSONManage;

import com.dropbox.core.v2.files.DownloadZipResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

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

    public static ArrayList<Object> getJsonOfMultimedia(Object result) {
        ArrayList<Object> arrOfKeyValues = new ArrayList<>();
        JSONParser jParser = new JSONParser();

        try{
            JSONObject jObject_root = (JSONObject) jParser.parse(result.toString());
            arrOfKeyValues.add((String) jObject_root.get(".tag"));
            arrOfKeyValues.add((String) jObject_root.get("name"));
            arrOfKeyValues.add((String) jObject_root.get("path_display"));
            arrOfKeyValues.add((Long) jObject_root.get("size"));
            arrOfKeyValues.add((String) jObject_root.get("id"));
            arrOfKeyValues.add((String) jObject_root.get("content_hash"));
            arrOfKeyValues.add((Boolean) jObject_root.get("is_downloadable"));

            if( jObject_root.containsKey("media_info") ){
                JSONObject jObject_media_info = (JSONObject) jObject_root.get("media_info");

                if( jObject_media_info.containsKey("metadata") ){
                    JSONObject jObject_metadata = (JSONObject) jObject_media_info.get("metadata");
                    arrOfKeyValues.set(0, (String) jObject_metadata.get(".tag"));

                    if(jObject_metadata.containsKey("dimensions")){
                        JSONObject jObject_dimensions = (JSONObject) jObject_metadata.get("dimensions");
                        arrOfKeyValues.add((Long) jObject_dimensions.get("width"));
                        arrOfKeyValues.add((Long) jObject_dimensions.get("height"));
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arrOfKeyValues;
    }

    public static ArrayList<Object> getJsonOfFolders(Object result) {
        ArrayList<Object> arrOfKeyValues = new ArrayList<>();
        JSONParser jParser = new JSONParser();

        try{
            JSONObject jObject_root = (JSONObject) jParser.parse(result.toString());
            if( jObject_root.containsKey("metadata") ){
                JSONObject jObject_metadata = (JSONObject) jObject_root.get("metadata");
                arrOfKeyValues.add((String) jObject_metadata.get("id"));
                arrOfKeyValues.add((String) jObject_metadata.get("name"));
                arrOfKeyValues.add((String) jObject_metadata.get("path_display"));
                arrOfKeyValues.add((String) jObject_metadata.get(".tag"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arrOfKeyValues;
    }
}
