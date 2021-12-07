package it.giuugcola.OOPProject.JSONManage;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import jdk.jshell.spi.ExecutionControl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class JSONHandler {

    public static Object toJSON(String text) {
        JSONParser jParser = new JSONParser();
        JSONObject jObject = null;
        try {
            jObject = (JSONObject) jParser.parse(text);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jObject;
    }
}
