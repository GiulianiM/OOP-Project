package it.giuugcola.OOPProject.restController;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.OOPProject.metaData.DownloadedContent;
import it.giuugcola.OOPProject.metaData.FileMinAvgMax;
import it.giuugcola.OOPProject.metaData.MultiMedia;
import it.giuugcola.OOPProject.settings.Constants;
import it.giuugcola.OOPProject.settings.DropboxClient;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CallsHandler implements Constants {

    public static ListFolderResult list_folder_root() {
        ListFolderResult metadata = null;

        try {
            metadata = DropboxClient.getClient()
                    .files()
                    .listFolder("");
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return metadata;
    }

    public static Metadata get_metadata(String path) {
        Metadata metadata = null;

        try {
            metadata = DropboxClient.getClient()
                    .files()
                    .getMetadata(path);
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
            metadata = DropboxClient.getClient()
                    .files()
                    .downloadBuilder(path)
                    .download(outputStream);
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
            metadata = DropboxClient.getClient()
                    .files()
                    .downloadZipBuilder(path)
                    .download(outputStream);
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

        String minFile = "", maxFile = "", minPhoto = "", maxPhoto = "", minVideo = "", maxVideo = "", avgFile = "", avgPhoto = "", avgVideo = "";

        if (!mapFiles.isEmpty()) {
            minFile = Collections.min(mapFiles.values());
            maxFile = Collections.max(mapFiles.values());
            double sum = 0.0;

            for (Map.Entry<String, String> entry : mapFiles.entrySet()) {
                if (entry.getValue().equals(minFile))
                    minFile = entry.getKey() + " " + minFile;
                if (entry.getValue().equals(maxFile))
                    maxFile = entry.getKey() + " " + maxFile;
                sum += Double.parseDouble(entry.getValue().substring(0, entry.getValue().length() - 2).replace(",", "."));
            }
            avgFile = String.format("%.2f", sum / mapFiles.size()) + "MB";
            fileMinAvgMax.getMinAvgMaxFile().add(minFile);
            fileMinAvgMax.getMinAvgMaxFile().add(avgFile);
            fileMinAvgMax.getMinAvgMaxFile().add(maxFile);
        }

        if (!mapPhotos.isEmpty()) {
            minPhoto = Collections.min(mapPhotos.values());
            maxPhoto = Collections.max(mapPhotos.values());
            double sum = 0.0;

            for (Map.Entry<String, String> entry : mapPhotos.entrySet()) {
                if (entry.getValue().equals(minPhoto))
                    minPhoto = entry.getKey() + " " + minPhoto;
                if (entry.getValue().equals(maxPhoto))
                    maxPhoto = entry.getKey() + " " + maxPhoto;
                sum += Double.parseDouble(entry.getValue().substring(0, entry.getValue().length() - 2).replace(",", "."));
            }
            avgPhoto = String.format("%.2f", sum / mapPhotos.size()) + "MB";
            fileMinAvgMax.getMinAvgMaxPhoto().add(minPhoto);
            fileMinAvgMax.getMinAvgMaxPhoto().add(avgPhoto);
            fileMinAvgMax.getMinAvgMaxPhoto().add(maxPhoto);
        }

        if (!mapVideos.isEmpty()) {
            minVideo = Collections.min(mapVideos.values());
            maxVideo = Collections.max(mapVideos.values());
            double sum = 0.0;

            for (Map.Entry<String, String> entry : mapVideos.entrySet()) {
                if (entry.getValue().equals(minVideo))
                    minVideo = entry.getKey() + " " + minVideo;
                if (entry.getValue().equals(maxVideo))
                    maxVideo = entry.getKey() + " " + maxVideo;
                sum += Double.parseDouble(entry.getValue().substring(0, entry.getValue().length() - 2).replace(",", "."));
            }
            avgVideo = String.format("%.2f", sum / mapVideos.size()) + "MB";
            fileMinAvgMax.getMinAvgMaxVideo().add(minVideo);
            fileMinAvgMax.getMinAvgMaxVideo().add(avgVideo);
            fileMinAvgMax.getMinAvgMaxVideo().add(maxVideo);
        }

        System.out.println(minFile + "|" + avgFile + "|" + maxFile + "\n" +
                minPhoto + "|" + avgPhoto + "|" + maxPhoto + "\n" +
                minVideo + "|" + avgVideo + "|" + maxVideo);

        return fileMinAvgMax;
    }

    public static DownloadedContent statsFiltered(DownloadedContent downloadList, String filter) {
        ArrayList<MultiMedia> filteredFiles = new ArrayList<>();
        String separator = ",";
        String between = "bt";

        if (filter.contains(",")) {
            int count = StringUtils.countOccurrencesOf(filter, separator);
            String[] filterSeparated = filter.split(",");
            if (count == 1) {
                Double compareValue = CallsHandler.checkInput(filterSeparated[1]);
                switch (filterSeparated[0]) {
                    case "<" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() < compareValue)
                                filteredFiles.add(m);
                        }
                    }
                    case ">" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() > compareValue)
                                filteredFiles.add(m);
                        }
                    }
                    case "<=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() <= compareValue)
                                filteredFiles.add(m);
                        }
                    }
                    case ">=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB() >= compareValue)
                                filteredFiles.add(m);
                        }
                    }
                    case "=" -> {
                        for (MultiMedia m : downloadList.getMultimedia()) {
                            if (m.getSizeMB().equals(compareValue))
                                filteredFiles.add(m);
                        }
                    }
                }
            } else if (count == 2) {
                if (filterSeparated[0].equals(between)) {
                    Double value1 = CallsHandler.checkInput(filterSeparated[1]);
                    Double value2 = CallsHandler.checkInput(filterSeparated[2]);
                    for (MultiMedia m : downloadList.getMultimedia()) {
                        if (value2 >= m.getSizeMB() && value1 <= m.getSizeMB())
                            filteredFiles.add(m);
                    }
                } else
                    System.out.println("Operatore non valido");
            } else
                System.out.println("Filtro invalido");
        } else if (filter.isEmpty()) {
            return downloadList;
        } else
            System.out.println("Nessun separatore");


        return new DownloadedContent(filteredFiles);
    }

    //Controllo presenza virgola, punto da input, se non presenti viene aggiunto il punto
    public static Double checkInput(String str) {
        System.out.println(str);
        double value = 0.0;
        if (str.contains(".")) {
            value = Double.parseDouble(str);
        } else if (str.contains(",")) {
            value = Double.parseDouble(str.replaceAll(",", "."));
        } else {
            value = Double.parseDouble(str.concat(".0"));
        }
        return value;
    }

    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private static String getFileOrFolderName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
