package it.giuugcola.oop.stream;
import it.giuugcola.oop.exceptions.DownloadException;
import it.giuugcola.oop.exceptions.FileException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.metadata.Downloaded;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Classe per la lettura su file.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class InputFromFile {

    /**
     * Percorso in cui vengono salvati i file.
     */
    private static final String PATH = "log/";

    /**
     * Nome del file contenente tutti i file multimediali scaricati.
     */
    private static final String FILE_MULTIMEDIA = "multimedia.txt";

    /**
     * Nome del file contenente tutti le cartelle scaricate.
     */
    private static final String FILE_FOLDER = "folder.txt";

    /**
     * Metodo per leggere dai file txt tutti i file e le cartelle scaricate.
     *
     * @param downloaded Oggetto {@link Downloaded} che conterrà tutti i file scaricati
     * @throws FileException          Se la lettura su file genera errori
     * @throws ParsingToJsonException Se la creazione del JSON genera errori
     */
    public InputFromFile(Downloaded downloaded) throws FileException, ParsingToJsonException, DownloadException {
        if (new File(PATH + FILE_MULTIMEDIA).exists())
            setArrayOfMultimedia(downloaded);
        if (new File(PATH + FILE_FOLDER).exists())
            setArrayOfFolders(downloaded);
    }

    /**
     * Metodo ausiliario di {@link #InputFromFile(Downloaded)}, che legge i file multimediali scaricati.
     *
     * @param downloaded Oggetto {@link Downloaded} che conterrà tutti i file scaricati
     */
    private void setArrayOfMultimedia(Downloaded downloaded) throws FileException, ParsingToJsonException, DownloadException {
        JSONParser jParser = new JSONParser();
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH + FILE_MULTIMEDIA))) {

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
                if(line.startsWith("}")){
                    JSONObject jObject = (JSONObject) jParser.parse(content.toString());
                    downloaded.addMultimedia(jObject);
                    content.setLength(0);
                }
            }

        } catch (IOException e) {
            throw new FileException("File non trovato, controlla il corretto inserimento! PATH': " + PATH + FILE_MULTIMEDIA+ "'");
        } catch (ParseException | ParsingToJsonException e) {
           throw new ParsingToJsonException("Errore nel JSON di input!");
        }
    }

    /**
     * Metodo ausiliario di {@link #InputFromFile(Downloaded)}, che legge le cartelle scaricate.
     *
     * @param downloaded Oggetto {@link Downloaded} che conterrà tutti i file scaricati
     */
    private void setArrayOfFolders(Downloaded downloaded) throws FileException, ParsingToJsonException, DownloadException {
        JSONParser jParser = new JSONParser();
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH + FILE_FOLDER))) {

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
                if(line.startsWith("}")){
                    JSONObject jObject = (JSONObject) jParser.parse(content.toString());
                    downloaded.addFolder(jObject);
                    content.setLength(0);
                }
            }

        } catch (IOException e) {
            throw new FileException("File non trovato, controlla il corretto inserimento! PATH': " + PATH + FILE_FOLDER+ "'");
        } catch (ParseException | ParsingToJsonException e) {
            throw new ParsingToJsonException("Errore nel JSON di input!");
        }
    }

}
