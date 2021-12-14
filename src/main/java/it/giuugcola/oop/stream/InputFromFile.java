package it.giuugcola.oop.stream;
import it.giuugcola.oop.exceptions.FileException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.metadata.Downloaded;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class InputFromFile {
    private final String path = "log/";
    private final String fileMultimedia = "fileMultimedia.txt";
    private final String fileFolder = "fileFolder.txt";

    public InputFromFile(Downloaded downloaded) throws FileException, ParsingToJsonException {
        if (new File(path + fileMultimedia).exists())
            setArrayOfMultimedia(downloaded);
        if (new File(path + fileFolder).exists())
            setArrayOfFolders(downloaded);
    }

    private void setArrayOfMultimedia(Downloaded downloaded) throws FileException, ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path + fileMultimedia))) {

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
            throw new FileException("File non trovato, controlla il corretto inserimento! path': " + path + fileMultimedia+ "'");
        } catch (ParseException | ParsingToJsonException e) {
           throw new ParsingToJsonException("Errore nel JSON di input!");
        }
    }

    private void setArrayOfFolders(Downloaded downloaded) throws FileException, ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path + fileFolder))) {

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
            throw new FileException("File non trovato, controlla il corretto inserimento! path': " + path + fileFolder+ "'");
        } catch (ParseException | ParsingToJsonException e) {
            throw new ParsingToJsonException("Errore nel JSON di input!");
        }
    }

}
