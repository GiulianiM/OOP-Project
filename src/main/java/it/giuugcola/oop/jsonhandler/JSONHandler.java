package it.giuugcola.oop.jsonhandler;

import it.giuugcola.oop.exceptions.FilterJsonException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.metadata.Downloaded;
import it.giuugcola.oop.metadata.MultiMedia;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che gestisce la lettura e la creazione dei JSON.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
@SuppressWarnings("unchecked")
public class JSONHandler {
    /**
     * Array contenente tutti i tag possibili per un file scaricato.
     */
    private static String[] tags = new String[]{"file", "photo", "video"};

    /**
     * Array contenente le key da assegnare ai JSONObject e ai JSONArray.
     */
    private static String[] keys = new String[]{"name", "size", "min", "max", "avg"};

    /**
     * Metodo per la conversione di testo in JSON.
     *
     * @param text Stringa di testo.
     * @return Restituisce text convertito in JSON.
     * @throws ParsingToJsonException Se non è possibile creare il JSON.
     */
    public static JSONObject objectToJson(Object text) throws ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        JSONObject jObjectRoot;
        try {
            jObjectRoot = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parametro 'text'");
        }

        return jObjectRoot;
    }

    /**
     * Crea il JSON di un oggetto {@link MultiMedia} con gli attributi di quest'ultimo.
     *
     * @param result      Oggetto di tipo {@link MultiMedia}.
     * @param jMultimedia {@link JSONObject} da popolare con result.
     * @throws ParsingToJsonException Se non è possibile creare il JSON.
     */
    public static void setJSONOfMultimedia(Object result, JSONOfMultimedia jMultimedia) throws ParsingToJsonException {
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
            throw new ParsingToJsonException("Errore nel parametro 'result'");
        }
    }

    /**
     * Crea il JSON di un oggetto {@link it.giuugcola.oop.metadata.Folder} con gli attributi di quest'ultimo.
     *
     * @param result  Oggetto di tipo {@link it.giuugcola.oop.metadata.Folder}.
     * @param jFolder {@link JSONObject} da popolare con result.
     * @throws ParsingToJsonException Se non è possibile creare il JSON.
     */
    public static void setJSONOfFolder(Object result, JSONOfFolder jFolder) throws ParsingToJsonException {
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
            throw new ParsingToJsonException("Errore nel parametro 'result'");
        }

    }

    /**
     * Crea il JSON di un oggetto {@link Downloaded} in base al filtro e al tag forniti.
     *
     * @param downloaded Oggetto di tipo {@link Downloaded}
     * @param filter     Filtro da applicare.
     * @param tag        Tipo di file.
     * @return JSONObject.
     * @throws FilterJsonException Se il filtro non è valido.
     */
    public static JSONObject filteredToJson(Downloaded downloaded, String filter, String tag) throws FilterJsonException {
        JSONObject listRoot = new JSONObject(); //root
        JSONObject subRoot = new JSONObject(); //sub-root

        //caso filtro e tag vuoti
        if (filter.isEmpty() && tag.isEmpty()) {
            subRoot.put(tags[0], getEntriesAll(downloaded.getMultimedia(), tags[0]));
            subRoot.put(tags[1], getEntriesAll(downloaded.getMultimedia(), tags[1]));
            subRoot.put(tags[2], getEntriesAll(downloaded.getMultimedia(), tags[2]));
        }
        //caso tag vuoto
        else if (!filter.isEmpty() && tag.isEmpty()) {
            subRoot.put(tags[0], getEntriesFiltered(downloaded, filter, tags[0]));
            subRoot.put(tags[1], getEntriesFiltered(downloaded, filter, tags[1]));
            subRoot.put(tags[2], getEntriesFiltered(downloaded, filter, tags[2]));
        } else {
            subRoot.put(tag, getEntriesFiltered(downloaded, filter, tag));
        }

        listRoot.put("request_time", getRequestDateTime());
        listRoot.put("file_tag", subRoot);


        return listRoot;
    }

    /**
     * Crea il JSON di un oggetto {@link Downloaded} suddividendo per tipo il minore, il maggiore e la dimensione media di tutti i file.
     *
     * @param downloaded Oggetto di tipo {@link Downloaded}
     * @param tag        Tipo di file.
     * @return JSONObject.
     */
    public static JSONObject minAvgMaxToJson(Downloaded downloaded, String tag) {
        JSONObject listRoot = new JSONObject();
        JSONObject subRoot = new JSONObject();
        //restituisco per tutti i tipi di file
        if (tag.isEmpty()) {
            subRoot.put(tags[0], getEntriesMinAvgMax(downloaded, tags[0]));
            subRoot.put(tags[1], getEntriesMinAvgMax(downloaded, tags[1]));
            subRoot.put(tags[2], getEntriesMinAvgMax(downloaded, tags[2]));
        }
        //restituisco solo per il tipo richiesto
        else
            subRoot.put(tag, getEntriesMinAvgMax(downloaded, tag));

        listRoot.put("request_time", getRequestDateTime());
        listRoot.put("file_tag", subRoot);

        return listRoot;
    }

    /**
     * Metodo ausiliario di {@link #filteredToJson(Downloaded, String, String)}, popola un JSONArray.
     *
     * @param downloaded Oggetto di tipo {@link Downloaded}
     * @param filter     Filtro da applicare.
     * @param tag        Tipo di file.
     * @return JSONArray.
     */
    private static JSONArray getEntriesFiltered(Downloaded downloaded, String filter, String tag) throws FilterJsonException {
        JSONArray jsonArray = new JSONArray();

        if (filter.isEmpty() && !tag.isEmpty()) {
            jsonArray = getEntriesAll(downloaded.getMultimedia(), tag);
        } else {
            if (filter.contains("(") && filter.contains(")")) {
                final String regex = "[()]";
                final String separator = ";";
                final String between = "bt";

                String[] filterSplitted = filter.split(regex);
                int count = StringUtils.countMatches(filterSplitted[1], separator);
                if (count == 0) {
                    double compareValue = JSONHandler.checkInput(filterSplitted[1]);
                    checkSizeAndTag(downloaded, filterSplitted[0], compareValue, jsonArray, tag);
                } else if (count == 1) {
                    String[] valueSplitted = filterSplitted[1].split(separator);
                    if (filterSplitted[0].equals(between)) {
                        double value1 = JSONHandler.checkInput(valueSplitted[0]);
                        double value2 = JSONHandler.checkInput(valueSplitted[1]);
                        for (MultiMedia m : downloaded.getMultimedia()) {
                            if (m.getTag().equals(tag) && value2 >= m.getSizeMB() && value1 <= m.getSizeMB())
                                jsonArray.add(addFile(m));
                        }
                    } else
                        throw new FilterJsonException("Parametro 'filter' non corretto! Operatore non valido. Prova con 'bt'");
                } else
                    throw new FilterJsonException("Parametro 'filter' non corretto! Inserire al massimo 2 numeri separati da un ';'");
            } else
                throw new FilterJsonException("Parametro 'filter' non corretto! Inserire i numeri all'interno delle parentesi tonde");
        }
        return jsonArray;
    }

    /**
     * Metodo ausiliario di {@link #minAvgMaxToJson(Downloaded, String)}, popola un JSONArray
     *
     * @param downloaded Oggetto di tipo {@link Downloaded}
     * @param tag        Tipo di file.
     * @return JSONArray
     */
    private static JSONArray getEntriesMinAvgMax(Downloaded downloaded, String tag) {
        JSONArray jArrayRoot = new JSONArray();

        if (downloaded.isAtLeastOne(tag)) {
            JSONObject minVal = new JSONObject();
            minVal.put(keys[0], downloaded.getMinSizeName(tag));
            minVal.put(keys[1], format(downloaded.getMinSize(tag)));

            JSONObject maxVal = new JSONObject();
            maxVal.put(keys[0], downloaded.getMaxSizeName(tag));
            maxVal.put(keys[1], format(downloaded.getMaxSize(tag)));

            JSONObject avgVal = new JSONObject();
            avgVal.put(keys[1], format(downloaded.getAvgSize(tag)));

            JSONObject jObjectRoot = new JSONObject();
            jObjectRoot.put(keys[2], minVal);
            jObjectRoot.put(keys[3], maxVal);
            jObjectRoot.put(keys[4], avgVal);

            jArrayRoot.add(jObjectRoot);

        }
        return jArrayRoot;
    }

    /**
     * Metodo ausiliario di {@link #filteredToJson(Downloaded, String, String)}, popola un JSONArray.
     *
     * @param multimedia Oggetto di tipo {@link Downloaded}
     * @param tag        Tipo di file.
     * @return JSONArray
     */
    private static JSONArray getEntriesAll(ArrayList<MultiMedia> multimedia, String tag) {
        JSONArray jsonArray = new JSONArray();

        for (MultiMedia m : multimedia) {
            if (m.getTag().equals(tag)) {
                jsonArray.add(addFile(m));
            }
        }

        return jsonArray;
    }

    /**
     * Metodo ausiliario di {@link #getEntriesFiltered(Downloaded, String, String)} e {@link #getEntriesAll(ArrayList, String)}, popola un JSONObject.
     *
     * @param m Oggetto di tipo {@link MultiMedia}.
     * @return JSONObject.
     */
    private static JSONObject addFile(MultiMedia m) {
        JSONObject sizeName = new JSONObject();
        sizeName.put(keys[0], m.getName());
        sizeName.put(keys[1], format(m.getSizeMB()));
        return sizeName;
    }

    /**
     * Metodo che controlla la corretta formattazione della stringa fornita in input. Se questa presenta una
     * virgola viene sostituita da un punto, mentre se entrambi sono assenti viene aggiunto ".0".
     * <blockquote>Fornendo in input "3,0" otterremo "3.0", invece se forniamo "3" si riceve "3.0".</blockquote>
     *
     * @param str Stringa da controllare.
     * @return Numero di tipo double estratto dalla stringa.
     */
    private static double checkInput(String str) throws FilterJsonException {
        double value;
        if (str.contains(".")) {
            if (StringUtils.countMatches(str, ".") > 1)
                throw new FilterJsonException("Parametro 'filter' non corretto! Inserire al massimo un punto");
            value = Double.parseDouble(str);
        } else if (str.contains(",")) {
            if (StringUtils.countMatches(str, ",") > 1)
                throw new FilterJsonException("Parametro 'filter' non corretto! Inserire al massimo una virgola");
            value = Double.parseDouble(str.replaceAll(",", "."));
        } else {
            value = Double.parseDouble(str.concat(".0"));
        }
        return value;
    }

    /**
     * Metodo ausiliario per ottenere la data attuale.
     *
     * @return String contenente la data formattata come "dd/MM/yyyy HH:mm:ss".
     */
    private static String getRequestDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * Metodo ausiliario di {@link #getEntriesFiltered(Downloaded, String, String)} per confrontare la dimensione e il tag di un file
     * col filtro e il tag forniti dall'utente.
     *
     * @param downloaded   Oggetto {@link Downloaded} contenente tutti i file scaricati
     * @param filter       Filtro ottenuto in input
     * @param compareValue Dimensione da comparare
     * @param jsonArray    Oggetto {@link JSONArray} a cui aggiungere il file
     * @param tag          Tipo di file
     * @throws FilterJsonException Se il parametro filter è errato
     */
    private static void checkSizeAndTag(Downloaded downloaded, String filter, Double compareValue, JSONArray jsonArray, String tag) throws FilterJsonException {
        for (MultiMedia m : downloaded.getMultimedia()) {
            if (tag.isEmpty()) {
                switch (filter) {
                    case "lt" -> {
                        if (m.getSizeMB() < compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "gt" -> {
                        if (m.getSizeMB() > compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "lte" -> {
                        if (m.getSizeMB() <= compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "gte" -> {
                        if (m.getSizeMB() >= compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "e" -> {
                        if (m.getSizeMB() == compareValue)
                            jsonArray.add(addFile(m));
                    }
                    default -> throw new FilterJsonException("Parametro 'filter' non corretto!");
                }
            } else {
                switch (filter) {
                    case "lt" -> {
                        if (m.getTag().equals(tag) && m.getSizeMB() < compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "gt" -> {
                        if (m.getTag().equals(tag) && m.getSizeMB() > compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "lte" -> {
                        if (m.getTag().equals(tag) && m.getSizeMB() <= compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "gte" -> {
                        if (m.getTag().equals(tag) && m.getSizeMB() >= compareValue)
                            jsonArray.add(addFile(m));
                    }
                    case "e" -> {
                        if (m.getTag().equals(tag) && m.getSizeMB() == compareValue)
                            jsonArray.add(addFile(m));
                    }
                    default -> throw new FilterJsonException("Parametro 'filter' non corretto!");
                }
            }
        }
    }

    /**
     * Metodo ausiliario per aggiungere MB a un numero.
     *
     * @param size numero double.
     * @return String contenente size + MB.
     */
    private static String format(double size) {
        return String.format("%.2f", size)+"MB";
    }
}
