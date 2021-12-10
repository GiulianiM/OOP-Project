package it.giuugcola.OOPProject.JSONManage;

import it.giuugcola.OOPProject.metaData.FileMinAvgMax;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Map;

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

    public static JSONObject toJSONstats(ArrayList<Map<String, String>> mapArray) {
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

    public static JSONObject toJSONMinAvgMax(FileMinAvgMax fileMinAvgMax) {
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

    public static void populateClassJSONOfMultimedia(Object result) {
        JSONParser jParser = new JSONParser();

        try {
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

            if (jObject_root.containsKey("media_info")) {
                JSONObject jObject_media_info = (JSONObject) jObject_root.get("media_info");

                if (jObject_media_info.containsKey("metadata")) {
                    JSONObject jObject_metadata = (JSONObject) jObject_media_info.get("metadata");
                    JSONOfMultimedia.setTag((String) jObject_metadata.get(".tag"));

                    if (jObject_metadata.containsKey("dimensions")) {
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

        try {
            JSONObject jObject_root = (JSONObject) jParser.parse(result.toString());
            if (jObject_root.containsKey("metadata")) {
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
