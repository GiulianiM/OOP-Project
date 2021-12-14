package it.giuugcola.oop.stream;


import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.giuugcola.oop.exceptions.FileException;
import org.json.simple.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputOnFile {
    private final String path = "log/";
    private final String fileMultimedia = "fileMultimedia.txt";
    private final String fileFolder = "fileFolder.txt";
    private final String fileMinAvgMax = "fileMinAvgMax.txt";
    private final String fileFiltered = "fileFiltered.txt";

    public void writeMultimedia(FileMetadata multimedia) throws FileException {
        getStringToAppend(fileMultimedia, multimedia.toString());
    }

    public void writeFolder(DownloadZipResult folder) throws FileException {
        getStringToAppend(fileFolder, folder.toString());
    }

    public void writeFiltered(JSONObject filtered) throws FileException {
        getStringToAppend(fileFiltered, filtered.toString());
    }

    public void writeMinAvgMax(JSONObject minAvgMax) throws FileException {
        getStringToAppend(fileMinAvgMax, minAvgMax.toString());
    }

    private void getStringToAppend(String fileName, String resultToString) throws FileException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.path + fileName, true))) {

            writer.append(getHumanReadableName(resultToString));
            writer.newLine();


        } catch (IOException e) {
            throw new FileException("File non trovato, controlla il corretto inserimento! path': " + path + fileMultimedia + "'");
        }
    }

    private String getHumanReadableName(String filteredToJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jElement = JsonParser.parseString(filteredToJson);
        return gson.toJson(jElement);
    }
}
