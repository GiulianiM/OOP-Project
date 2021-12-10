package it.giuugcola.OOPProject.metaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileMap {

    Map<String, String> files = new HashMap<>();
    Map<String, String> photos = new HashMap<>();
    Map<String, String> videos = new HashMap<>();

    public ArrayList<Map<String, String>> populateMaps(DownloadedContent dlc) {
        long byteToMb = 1024 * 1024;

        if (!dlc.getMultimedia().isEmpty()) {
            for (MultiMedia m : dlc.getMultimedia()) {
                if (m.getClass() == File.class) {
                    files.put(m.getName(), String.format("%.2f", (double) m.getSize() / byteToMb) + "MB");
                }
                if (m.getClass() == Media.class) {
                    if (((Media) m).getTag().equals("photo"))
                        photos.put(m.getName(), String.format("%.2f", (double) m.getSize() / byteToMb) + "MB");
                    else
                        videos.put(m.getName(), String.format("%.2f", (double) m.getSize() / byteToMb) + "MB");
                }
            }
        }//TODO Else statement

        ArrayList<Map<String, String>> mapArray = new ArrayList<>();

        mapArray.add(files);
        mapArray.add(photos);
        mapArray.add(videos);

        System.out.println(files);
        System.out.println(photos);
        System.out.println(videos);

        return mapArray;
    }

}
