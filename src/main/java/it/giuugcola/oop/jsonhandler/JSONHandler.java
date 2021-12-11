package it.giuugcola.oop.jsonhandler;

import it.giuugcola.oop.metadata.FileMinAvgMax;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unchecked") //per i warning dei put nei JSONObject e JSONArray
public class JSONHandler {

    //da Oggetto a Json
    public static JSONObject toJson(Object text) {
        JSONParser jParser = new JSONParser();
        JSONObject jObjectRoot = null;
        try {
            jObjectRoot = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jObjectRoot;
    }

    public static JSONObject toJsonStats(ArrayList<Map<String, String>> mapArray) {
        Map<String, String> mapFiles = mapArray.get(0);
        Map<String, String> mapPhotos = mapArray.get(1);
        Map<String, String> mapVideos = mapArray.get(2);

        JSONObject list = new JSONObject(); //root
        JSONObject fileList = new JSONObject(); //sub-root

        fileList.put("File", addFilesStats(mapFiles));
        fileList.put("Foto", addFilesStats(mapPhotos));
        fileList.put("Video", addFilesStats(mapVideos));

        list.put("Tipo_file", fileList);

        return list;
    }

    private static JSONArray addFilesStats(Map<String, String> map) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            JSONObject sizeName = new JSONObject();
            sizeName.put("Name", entry.getKey());
            sizeName.put("Size", entry.getValue());
            jsonArray.add(sizeName);
        }

        return jsonArray;
    }

    public static JSONObject toJsonMinAvgMax(FileMinAvgMax fileMinAvgMax) {
        ArrayList<String> fileArrayList = fileMinAvgMax.getMinAvgMaxFile();
        ArrayList<String> photoArrayList = fileMinAvgMax.getMinAvgMaxPhoto();
        ArrayList<String> videoArrayList = fileMinAvgMax.getMinAvgMaxVideo();

        JSONObject minAvgMax = new JSONObject(); // root
        JSONObject fileList = new JSONObject();

        fileList.put("File", addFilesMAM(fileArrayList));
        fileList.put("Foto", addFilesMAM(photoArrayList));
        fileList.put("Video", addFilesMAM(videoArrayList));

        minAvgMax.put("Tipo_file", fileList);

        return minAvgMax;
    }

    private static JSONObject addFilesMAM(ArrayList<String> fileList) {
        JSONObject fileMin = new JSONObject();
        JSONObject fileAvg = new JSONObject();
        JSONObject fileMax = new JSONObject();
        JSONObject fileMAM = new JSONObject();

        String[] fileMinNameSize;
        String[] fileMaxNameSize;

        for (String s : fileList) {
            if (s.contains(" ") && s.equals(fileList.get(0))) {
                fileMinNameSize = s.split("\\s+");
                fileMin.put("Name", fileMinNameSize[0]);
                fileMin.put("Size", fileMinNameSize[1]);
            }
            if (s.contains(" ") && s.equals(fileList.get(2))) {
                fileMaxNameSize = s.split("\\s+");
                fileMax.put("Name", fileMaxNameSize[0]);
                fileMax.put("Size", fileMaxNameSize[1]);
            } else fileAvg.put("Size", s);
        }

        fileMAM.put("Min", fileMin);
        fileMAM.put("Avg", fileAvg);
        fileMAM.put("Max", fileMax);

        return fileMAM;

    }

    public static void setJSONOfMultimedia(Object result, JSONOfMultimedia jMultimedia) {
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
            e.printStackTrace();
        }
    }

    public static void setJSONOfFolder(Object result, JSONOfFolder jFolder) {
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
            e.printStackTrace();
        }

    }

}
