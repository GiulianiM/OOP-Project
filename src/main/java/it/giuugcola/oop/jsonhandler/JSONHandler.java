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

import java.util.ArrayList;

@SuppressWarnings("unchecked") //per i warning dei put nei JSONObject e JSONArray
public class JSONHandler {

    public static JSONObject toJson(Object text) throws ParsingToJsonException {
        JSONParser jParser = new JSONParser();
        JSONObject jObjectRoot;
        try {
            jObjectRoot = (JSONObject) jParser.parse(text.toString());

        } catch (ParseException e) {
            throw new ParsingToJsonException("Errore nel parametro 'text'");
        }

        return jObjectRoot;
    }

    public static JSONObject toJsonFiltered(Downloaded downloaded, String filter, String tag) throws FilterJsonException {
        JSONObject listRoot = new JSONObject(); //root
        JSONObject listSubRoot = new JSONObject(); //sub-root

        //caso filtro e tag vuoti
        if (filter.isEmpty() && tag.isEmpty()) {
            listSubRoot.put("file", getEntriesAll(downloaded.getMultimedia(), "file"));
            listSubRoot.put("photo", getEntriesAll(downloaded.getMultimedia(), "photo"));
            listSubRoot.put("video", getEntriesAll(downloaded.getMultimedia(), "video"));

            listRoot.put("Tipo_file", listSubRoot);
        }
        //caso tag vuoto
        else if (!filter.isEmpty() && tag.isEmpty()) {
            listSubRoot.put("file", getEntriesFiltered(downloaded, filter, "file"));
            listSubRoot.put("photo", getEntriesFiltered(downloaded, filter, "photo"));
            listSubRoot.put("video", getEntriesFiltered(downloaded, filter, "video"));

            listRoot.put("Tipo_file", listSubRoot);
        }
        //caso filtro vuoto (tag è pieno)
        else if (filter.isEmpty()) { //todo sono qui
            listSubRoot.put(tag, getEntriesFiltered(downloaded, filter, tag));

            listRoot.put("Tipo_file", listSubRoot);
            //caso entrambi pieni
        } else {
            listSubRoot.put(tag, getEntriesFiltered(downloaded, filter, tag));

            listRoot.put("Tipo_file", listSubRoot);
        }

        return listRoot;
    }

    public static JSONObject toJsonMinAvgMax(Downloaded downloaded, String tag) {
        JSONObject root = new JSONObject();
        JSONObject subRoot = new JSONObject();

        //restituisco per tutti i tipi di file
        if (tag.isEmpty()) {
            subRoot.put("photo", getEntriesMinAvgMax(downloaded, "photo"));
            subRoot.put("video", getEntriesMinAvgMax(downloaded, "video"));
            subRoot.put("file", getEntriesMinAvgMax(downloaded, "file"));
        }
        //restituisco solo per il tipo richiesto
        else
            subRoot.put(tag, getEntriesMinAvgMax(downloaded, tag));

        root.put("Tipo_file", subRoot);

        return root;
    }

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

    private static JSONArray getEntriesMinAvgMax(Downloaded downloaded, String tag) {
        JSONArray jArrayRoot = new JSONArray();

        if (downloaded.isAtLeastOne(tag)) {
            JSONObject minVal = new JSONObject();
            minVal.put("Name", downloaded.getMinSizeName(tag));
            minVal.put("Size", format(downloaded.getMinSize(tag)));

            JSONObject maxVal = new JSONObject();
            maxVal.put("Name", downloaded.getMaxSizeName(tag));
            maxVal.put("Size", format(downloaded.getMaxSize(tag)));

            JSONObject avgVal = new JSONObject();
            avgVal.put("Size", format(downloaded.getAvgSize(tag)));

            JSONObject jObjectRoot = new JSONObject();
            jObjectRoot.put("Min", minVal);
            jObjectRoot.put("Max", maxVal);
            jObjectRoot.put("Avg", avgVal);

            jArrayRoot.add(jObjectRoot);

        }
        return jArrayRoot;
    }

    private static JSONArray getEntriesAll(ArrayList<MultiMedia> multimedia, String tag) {
        JSONArray jsonArray = new JSONArray();

        for (MultiMedia m : multimedia) {
            if (m.getTag().equals(tag)) {
                jsonArray.add(addFile(m));
            }
        }

        return jsonArray;
    }

    private static JSONArray getEntriesFiltered(Downloaded downloaded, String filter, String tag) throws FilterJsonException {
        JSONArray jsonArray = new JSONArray();

        if (filter.isEmpty() && !tag.isEmpty()) {
            jsonArray = getEntriesAll(downloaded.getMultimedia(), tag);
        } else {
            if (filter.contains("(") && filter.contains(")")) {
                final String regex = "[()]";
                final String separator = ";";
                final String between = "bt";
                String[] fiterSplitted = filter.split(regex);
                int count = StringUtils.countMatches(fiterSplitted[1], separator);
                if (count == 0) {
                    double compareValue = JSONHandler.checkInput(fiterSplitted[1]);
                    if (tag.isEmpty()) {
                        switch (fiterSplitted[0]) {
                            case "lt" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getSizeMB() < compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "gt" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getSizeMB() > compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "lte" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getSizeMB() <= compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "gte" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getSizeMB() >= compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "e" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getSizeMB() == compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            default -> throw new FilterJsonException("Parametro 'filter' non corretto! Al massimo 1 numero fra parentesi tonde. ");
                        }
                    } else {
                        switch (fiterSplitted[0]) {
                            case "lt" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getTag().equals(tag) && m.getSizeMB() < compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "gt" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getTag().equals(tag) && m.getSizeMB() > compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "lte" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getTag().equals(tag) && m.getSizeMB() <= compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "gte" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getTag().equals(tag) && m.getSizeMB() >= compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            case "e" -> {
                                for (MultiMedia m : downloaded.getMultimedia()) {
                                    if (m.getTag().equals(tag) && m.getSizeMB() == compareValue)
                                        jsonArray.add(addFile(m));
                                }
                            }
                            default -> throw new FilterJsonException("Parametro 'filter' non corretto! Al massimo 1 numero fra parentesi tonde. ");
                        }
                    }
                } else if (count == 1) {
                    String[] valueSplitted = fiterSplitted[1].split(separator);
                    if (fiterSplitted[0].equals(between)) {
                        Double value1 = JSONHandler.checkInput(valueSplitted[0]);
                        Double value2 = JSONHandler.checkInput(valueSplitted[1]);
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

    private static JSONObject addFile(MultiMedia m) {
        JSONObject sizeName = new JSONObject();
        sizeName.put("Name", m.getName());
        sizeName.put("Size", format(m.getSizeMB()));
        return sizeName;
    }

    //Controllo presenza virgola, punto da input, se non presenti viene aggiunto il punto
    private static Double checkInput(String str) throws FilterJsonException {
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

    private static String format(double size) {
        return size + "MB";
    }
}
