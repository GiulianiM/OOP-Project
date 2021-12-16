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

/**
 * Classe per la scrittura su file.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */

public class OutputOnFile {
    /**
     * Percorso in cui vengono salvati i file.
     */
    private final String path = "log/";

    /**
     * Nome del file contenente tutti i file multimediali scaricati.
     */
    private final String fileMultimedia = "fileMultimedia.txt";

    /**
     * Nome del file contenente tutti le cartelle scaricate.
     */
    private final String fileFolder = "fileFolder.txt";

    /**
     * Nome del file contenente le chiamate a {@link it.giuugcola.oop.restcontroller.Controller#statsMinAvgMax(String)}
     */
    private final String fileMinAvgMax = "fileMinAvgMax.txt";

    /**
     * Nome del file contenente le chiamate a {@link it.giuugcola.oop.restcontroller.Controller#stats(String, String)}
     */
    private final String fileFiltered = "fileFiltered.txt";

    /**
     * Scrive sul file txt il file scaricato da Dropbox.
     *
     * @param multimedia File scaricato
     * @throws FileException Se non trova il file txt su cui salvare le informazioni
     */
    public void writeMultimedia(FileMetadata multimedia) throws FileException {
        getStringToAppend(fileMultimedia, multimedia.toString());
    }

    /**
     * Scrive sul file txt la cartella scaricata da Dropbox.
     *
     * @param folder Cartella scaricata
     * @throws FileException Se non trova il file txt su cui salvare le informazioni
     */
    public void writeFolder(DownloadZipResult folder) throws FileException {
        getStringToAppend(fileFolder, folder.toString());
    }

    /**
     * Scrive sul file txt la chiamata a {@link it.giuugcola.oop.restcontroller.Controller#stats(String, String)}.
     *
     * @param filtered JSON restituito dal metodo stats
     * @throws FileException Se non trova il file txt su cui salvare le informazioni
     */
    public void writeFiltered(JSONObject filtered) throws FileException {
        getStringToAppend(fileFiltered, filtered.toString());
    }

    /**
     * Scrive sul file txt la chiamata a {@link it.giuugcola.oop.restcontroller.Controller#statsMinAvgMax(String)}.
     *
     * @param minAvgMax JSON restituito dal metodo statsMinAvgMax
     * @throws FileException Se non trova il file txt su cui salvare le informazioni
     */
    public void writeMinAvgMax(JSONObject minAvgMax) throws FileException {
        getStringToAppend(fileMinAvgMax, minAvgMax.toString());
    }

    /**
     * Metodo ausiliario per aggiungere contenuto ai file txt.
     *
     * @param fileName Nome del file su cui scrivere
     * @param resultToString Contenuto da aggiungere
     * @throws FileException Se non trova il file su cui scrivere.
     */
    private void getStringToAppend(String fileName, String resultToString) throws FileException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.path + fileName, true))) {

            writer.append(getHumanReadableName(resultToString));
            writer.newLine();


        } catch (IOException e) {
            throw new FileException("File non trovato, controlla il corretto inserimento! path': " + path + fileMultimedia + "'");
        }
    }

    /**
     * Trasforma una stringa JSON in modo che sia leggibile senza sforzi.
     *
     * @param filteredToJson Stringa JSON
     * @return Stringa convertita
     */
    private String getHumanReadableName(String filteredToJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jElement = JsonParser.parseString(filteredToJson);
        return gson.toJson(jElement);
    }
}
