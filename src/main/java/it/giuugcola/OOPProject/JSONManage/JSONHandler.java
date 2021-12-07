package it.giuugcola.OOPProject.JSONManage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {

    public static JSONObject toJSON(Object text) {
        JSONParser jParser = new JSONParser();
        JSONObject jObject = null;
        try {
            jObject = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jObject;
    }
}
