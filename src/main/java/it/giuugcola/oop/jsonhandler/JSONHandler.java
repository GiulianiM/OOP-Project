package it.giuugcola.oop.jsonhandler;

import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.metadata.Downloaded;
import it.giuugcola.oop.metadata.MultiMedia;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

@SuppressWarnings("unchecked") //per i warning dei put nei JSONObject e JSONArray
public class JSONHandler {
    private static final long MEGABYTE = 1024L * 1024L;

    public static JSONObject toJson(Object text) throws ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        JSONObject jObjectRoot;
        try {
            jObjectRoot = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parametro 'text'");
        }

        return jObjectRoot;
    }

    public static JSONObject toJsonFiltered(Downloaded downloaded) {
        JSONObject listRoot = new JSONObject(); //root
        JSONObject listSubRoot = new JSONObject(); //sub-root

        listSubRoot.put("file", getEntriesAll(downloaded.getMultimedia(), "file"));
        listSubRoot.put("photo", getEntriesAll(downloaded.getMultimedia(), "photo"));
        listSubRoot.put("video", getEntriesAll(downloaded.getMultimedia(), "video"));

        listRoot.put("Tipo_file", listSubRoot);

        return listRoot;
    }

    public static JSONObject toJsonMinAvgMax(Downloaded downloaded, String tag) {
        JSONObject root = new JSONObject();

        //restituisco per tutti i tipi di file
        if (tag.equals("")) {
            root.put("photo", getEntriesMinAvgMax(downloaded, "photo"));
            root.put("video", getEntriesMinAvgMax(downloaded, "video"));
            root.put("file", getEntriesMinAvgMax(downloaded, "file"));
        }
        //restituisco solo per il tipo richiesto
        else
            root.put(tag, getEntriesMinAvgMax(downloaded, tag));

        return root;
    }

    public static void setJSONOfMultimedia(Object result, JSONOfMultimedia jMultimedia) throws ParsingToJsonException {
        JSONParser jParser = new JSONParser();

        String tag;
        long width = 0;
        long height = 0;


        try {
            JSONObject jObjectRoot = (JSONObject) jParser.parse(result.toString());
            tag = (String) jObjectRoot.get(".tag");

            if (jObjectRoot.containsKey("media_info")) {
                JSONObject jObjectMediaInfo = (JSONObject) jObjectRoot.get("media_info");
                JSONObject jObjectMetadata = (JSONObject) jObjectMediaInfo.get("metadata");
                JSONObject jObjectDimensions = (JSONObject) jObjectMetadata.get("dimensions");

                tag = (String) jObjectMetadata.get(".tag");
                width = (Long) jObjectDimensions.get("width");
                height = (Long) jObjectDimensions.get("height");
            }

            jMultimedia.setPathDisplay((String) jObjectRoot.get("path_display"));
            jMultimedia.setRev((String) jObjectRoot.get("rev"));
            jMultimedia.setSize((Long) jObjectRoot.get("size"));
            jMultimedia.setServerModified((String) jObjectRoot.get("server_modified"));
            jMultimedia.setPathLower((String) jObjectRoot.get("path_lower"));
            jMultimedia.setDownloadable((Boolean) jObjectRoot.get("is_downloadable"));
            jMultimedia.setName((String) jObjectRoot.get("name"));
            jMultimedia.setId((String) jObjectRoot.get("id"));
            jMultimedia.setContentHash((String) jObjectRoot.get("content_hash"));
            jMultimedia.setClientModified((String) jObjectRoot.get("client_modified"));
            jMultimedia.setTag(tag);
            jMultimedia.setWidth(width);
            jMultimedia.setHeight(height);

        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parametro 'result'");
        }
    }

    public static void setJSONOfFolder(Object result, JSONOfFolder jFolder) throws ParsingToJsonException {
        JSONParser jParser = new JSONParser();

        try {
            JSONObject jObjectRoot = (JSONObject) jParser.parse(result.toString());
            if (jObjectRoot.containsKey("metadata")) {
                JSONObject jObjectMetadata = (JSONObject) jObjectRoot.get("metadata");

                jFolder.setPathDisplay((String) jObjectMetadata.get("path_display"));
                jFolder.setPathDisplay((String) jObjectMetadata.get("path_lower"));
                jFolder.setName((String) jObjectMetadata.get("name"));
                jFolder.setTag((String) jObjectMetadata.get(".tag"));
                jFolder.setId((String) jObjectMetadata.get("id"));

            }
        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parametro 'result'");
        }

    }


    private static JSONArray getEntriesMinAvgMax(Downloaded downloaded, String tag) {
        JSONArray jArrayRoot = new JSONArray();

        if (downloaded.isAtLeastOne(tag)) {
            JSONObject minVal = new JSONObject();
            minVal.put("Name", downloaded.getMinSizeName(tag));
            minVal.put("Size", format(downloaded.getMinSize(tag)));

            JSONObject maxVal = new JSONObject();
            maxVal.put("Name", downloaded.getMaxSizeName(tag));
            maxVal.put("Size", format(downloaded.getMaxSize(tag)));

            JSONObject avgVal = new JSONObject();
            avgVal.put("Size", format(downloaded.getAvgSize(tag)));

            JSONObject jObjectRoot = new JSONObject();
            jObjectRoot.put("min", minVal);
            jObjectRoot.put("max", maxVal);
            jObjectRoot.put("avg", avgVal);

            jArrayRoot.add(jObjectRoot);
            return jArrayRoot;

        } else
            return jArrayRoot;
    }

    private static JSONArray getEntriesAll(ArrayList<MultiMedia> multimedia, String tag) {
        JSONArray jsonArray = new JSONArray();

        for (MultiMedia m : multimedia) {
            if (m.getTag().equals(tag)) {
                JSONObject sizeName = new JSONObject();
                sizeName.put("Name", m.getName());
                sizeName.put("Size", format(m.getSizeMB()));
                jsonArray.add(sizeName);
            }
        }

        return jsonArray;
    }

    private static String format(double size) {
        return size + "MB";
    }
}
