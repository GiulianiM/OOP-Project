package it.giuugcola.oop.jsonhandler;

import it.giuugcola.oop.metadata.FileMinAvgMax;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;

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

    //todo metodo ausiliario come in CallsHandler-
    public static JSONObject toJsonStats(ArrayList<Map<String, String>> mapArray) {
        Map<String, String> mapFiles = mapArray.get(0);
        Map<String, String> mapPhotos = mapArray.get(1);
        Map<String, String> mapVideos = mapArray.get(2);

        JSONObject list = new JSONObject(); //root

        JSONObject fileList = new JSONObject(); //sub-root

        JSONArray fileArray = new JSONArray();
        JSONArray photoArray = new JSONArray();
        JSONArray videoArray = new JSONArray();

        for (Map.Entry<String, String> entry : mapFiles.entrySet()) {
            JSONObject sizeNameFiles = new JSONObject();
            sizeNameFiles.put("Size", entry.getValue());
            sizeNameFiles.put("Name", entry.getKey());
            fileArray.add(sizeNameFiles);
        }

        for (Map.Entry<String, String> entry : mapPhotos.entrySet()) {
            JSONObject sizeNamePhotos = new JSONObject();
            sizeNamePhotos.put("Name", entry.getKey());
            sizeNamePhotos.put("Size", entry.getValue());
            photoArray.add(sizeNamePhotos);

        }

        for (Map.Entry<String, String> entry : mapVideos.entrySet()) {
            JSONObject sizeNameVideo = new JSONObject();
            sizeNameVideo.put("Name", entry.getKey());
            sizeNameVideo.put("Size", entry.getValue());
            videoArray.add(sizeNameVideo);
        }

        fileList.put("Video", videoArray);
        fileList.put("Foto", photoArray);
        fileList.put("File", fileArray);

        list.put("Tipo_file", fileList);

        return list;
    }

    public static JSONObject toJsonMinAvgMax(FileMinAvgMax fileMinAvgMax) {
        ArrayList<String> fileArrayList = fileMinAvgMax.getMinAvgMaxFile();
        ArrayList<String> photoArrayList = fileMinAvgMax.getMinAvgMaxPhoto();
        ArrayList<String> videoArrayList = fileMinAvgMax.getMinAvgMaxVideo();

        String[] fileMin = new String[2];
        String[] fileMax = new String[2];

        String[] photoMin = new String[2];
        String[] photoMax = new String[2];

        String[] videoMin = new String[2];
        String[] videoMax = new String[2];

        JSONObject minAvgMax = new JSONObject(); // root

        JSONObject fileList = new JSONObject();

        JSONObject File = new JSONObject();
        JSONObject Photo = new JSONObject();
        JSONObject Video = new JSONObject();

        JSONObject MinFile = new JSONObject();
        JSONObject AvgFile = new JSONObject();
        JSONObject MaxFile = new JSONObject();

        JSONObject MinPhoto = new JSONObject();
        JSONObject AvgPhoto = new JSONObject();
        JSONObject MaxPhoto = new JSONObject();

        JSONObject MinVideo = new JSONObject();
        JSONObject AvgVideo = new JSONObject();
        JSONObject MaxVideo = new JSONObject();

        for (String s : fileArrayList) {
            if (s.contains(" ") && s.equals(fileArrayList.get(0))) {
                fileMin = s.split("\\s+");
                MinFile.put("Name", fileMin[0]);
                MinFile.put("Size", fileMin[1]);
            }
            if (s.contains(" ") && s.equals(fileArrayList.get(2))) {
                fileMax = s.split("\\s+");
                MaxFile.put("Name", fileMax[0]);
                MaxFile.put("Size", fileMax[1]);
            } else AvgFile.put("Size", s);
        }

        for (String s : photoArrayList) {
            if (s.contains(" ") && s.equals(photoArrayList.get(0))) {
                photoMin = s.split("\\s+");
                MinPhoto.put("Name", photoMin[0]);
                MinPhoto.put("Size", photoMin[1]);
            }
            if (s.contains(" ") && s.equals(photoArrayList.get(2))) {
                photoMax = s.split("\\s+");
                MaxPhoto.put("Name", photoMax[0]);
                MaxPhoto.put("Size", photoMax[1]);
            } else AvgPhoto.put("Size", s);
        }

        for (String s : videoArrayList) {
            if (s.contains(" ") && s.equals(videoArrayList.get(0))) {
                videoMin = s.split("\\s+");
                MinVideo.put("Name", videoMin[0]);
                MinVideo.put("Size", videoMin[1]);
            }
            if (s.contains(" ") && s.equals(videoArrayList.get(2))) {
                videoMax = s.split("\\s+");
                MaxVideo.put("Name", videoMax[0]);
                MaxVideo.put("Size", videoMax[1]);
            } else AvgVideo.put("Size", s);
        }

        File.put("Min", MinFile);
        File.put("Avg", AvgFile);
        File.put("Max", MaxFile);

        Photo.put("Min", MinPhoto);
        Photo.put("Avg", AvgPhoto);
        Photo.put("Max", MaxPhoto);

        Video.put("Min", MinVideo);
        Video.put("Avg", AvgVideo);
        Video.put("Max", MaxVideo);

        fileList.put("File", File);
        fileList.put("Foto", Photo);
        fileList.put("Video", Video);

        minAvgMax.put("Tipo_file", fileList);

        return minAvgMax;
    }

    public static void setJSONOfMultimedia(Object result, JSONOfMultimedia jMultimedia) {
        JSONParser jParser = new JSONParser();

        String tag = null;
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
