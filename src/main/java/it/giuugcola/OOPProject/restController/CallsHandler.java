package it.giuugcola.OOPProject.restController;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.OOPProject.metaData.DownloadedContent;
import it.giuugcola.OOPProject.metaData.FileMinAvgMax;
import it.giuugcola.OOPProject.metaData.MultiMedia;
import it.giuugcola.OOPProject.settings.DropboxClient;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

public class CallsHandler {

    private static final String DOWNLOAD_FOLDER_PATH = "Downloads";

    public static ListFolderResult list_folder_root() {
        ListFolderResult metadata = null;

        try {
            metadata = DropboxClient.getClient().files().listFolder("");
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static Metadata get_metadata(String path) {
        Metadata metadata = null;

        try {
            metadata = DropboxClient.getClient().files().getMetadata(path);
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static FileMetadata download(String path) {
        String download_where = DOWNLOAD_FOLDER_PATH + "/" + getFileOrFolderName(path);

        FileMetadata metadata = null;
        try {
            OutputStream outputStream = new FileOutputStream(download_where);
            metadata = DropboxClient.getClient().files().downloadBuilder(path).download(outputStream);
            outputStream.close();
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static DownloadZipResult download_zip(String path) {
        String download_where = DOWNLOAD_FOLDER_PATH + "/" + getFileOrFolderName(path) + ".zip";

        DownloadZipResult metadata = null;
        try {
            OutputStream outputStream = new FileOutputStream(download_where);
            metadata = DropboxClient.getClient().files().downloadZipBuilder(path).download(outputStream);
            outputStream.close();
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static FileMinAvgMax statsMAM(ArrayList<Map<String, String>> mapArray) {
        Map<String, String> mapFiles = mapArray.get(0); //Name, size
        Map<String, String> mapPhotos = mapArray.get(1);
        Map<String, String> mapVideos = mapArray.get(2);

        FileMinAvgMax fileMinAvgMax = new FileMinAvgMax();

        if (!Stream.of(mapFiles, mapPhotos, mapVideos).allMatch(Map::isEmpty)) {
            mapStats(mapFiles, fileMinAvgMax, "file");
            mapStats(mapPhotos, fileMinAvgMax, "photo");
            mapStats(mapVideos, fileMinAvgMax, "video");
        }

        return fileMinAvgMax;
    }

    public static DownloadedContent statsFiltered(DownloadedContent downloadList, String filter) {
        ArrayList<MultiMedia> filteredFiles = new ArrayList<>();
        final String regex = "[()]";
        final String separator = ";";
        final String between = "bt";

        if (filter.contains("(") && filter.contains(")")) {
            String[] fiterSplitted = filter.split(regex);
            int count = StringUtils.countMatches(fiterSplitted[1], separator);
            if (count == 0) {
                Double compareValue = CallsHandler.checkInput(fiterSplitted[1]);
                switch (fiterSplitted[0]) {
                    case "<" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() < compareValue) filteredFiles.add(m);
                        }
                    }
                    case ">" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() > compareValue) filteredFiles.add(m);
                        }
                    }
                    case "<=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() <= compareValue) filteredFiles.add(m);
                        }
                    }
                    case ">=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() >= compareValue) filteredFiles.add(m);
                        }
                    }
                    case "=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB().equals(compareValue)) filteredFiles.add(m);
                        }
                    }
                }
            } else if (count == 1) {
                String[] valueSplitted = fiterSplitted[1].split(separator);
                if (fiterSplitted[0].equals(between)) {
                    Double value1 = CallsHandler.checkInput(valueSplitted[0]);
                    Double value2 = CallsHandler.checkInput(valueSplitted[1]);
                    for (MultiMedia m : downloadList.getMultimedia()) {
                        if (value2 >= m.getSizeMB() && value1 <= m.getSizeMB()) filteredFiles.add(m);
                    }
                } else System.out.println("Operatore non valido");
            } else System.out.println("Filtro invalido");
        } else if (filter.isEmpty()) {
            return downloadList;
        } else System.out.println("Non ci sono le parentesi");


        return new DownloadedContent(filteredFiles);
    }

    //Controllo presenza virgola, punto da input, se non presenti viene aggiunto il punto
    private static Double checkInput(String str) {
        System.out.println(str);
        double value;
        if (str.contains(".")) {
            value = Double.parseDouble(str);
        } else if (str.contains(",")) {
            value = Double.parseDouble(str.replaceAll(",", "."));
        } else {
            value = Double.parseDouble(str.concat(".0"));
        }
        return value;
    }

    private static void mapStats(Map<String, String> map, FileMinAvgMax fileMinAvgMax, String type) {
        System.out.println(map);
        if (!map.isEmpty()) {
            String[] types = {"file", "photo", "video"};
            String min = Collections.min(map.values());
            String max = Collections.max(map.values());
            double sum = 0.0;

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals(min)) min = entry.getKey() + " " + min;
                if (entry.getValue().equals(max)) max = entry.getKey() + " " + max;
                sum += Double.parseDouble(entry.getValue().substring(0, entry.getValue().length() - 2).replace(",", "."));
            }
            String avg = String.format("%.2f", sum / map.size()) + "MB";
            if (type.equals(types[0])) {
                fileMinAvgMax.getMinAvgMaxFile().add(min);
                fileMinAvgMax.getMinAvgMaxFile().add(avg);
                fileMinAvgMax.getMinAvgMaxFile().add(max);
            } else if (type.equals(types[1])) {
                fileMinAvgMax.getMinAvgMaxPhoto().add(min);
                fileMinAvgMax.getMinAvgMaxPhoto().add(avg);
                fileMinAvgMax.getMinAvgMaxPhoto().add(max);
            } else if (type.equals(types[2])) {
                fileMinAvgMax.getMinAvgMaxVideo().add(min);
                fileMinAvgMax.getMinAvgMaxVideo().add(avg);
                fileMinAvgMax.getMinAvgMaxVideo().add(max);
            }
        }
    }

    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private static String getFileOrFolderName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
