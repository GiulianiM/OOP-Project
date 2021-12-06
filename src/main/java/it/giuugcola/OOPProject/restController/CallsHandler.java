package it.giuugcola.OOPProject.restController;

import it.giuugcola.OOPProject.settings.Constants;
import it.giuugcola.OOPProject.settings.HTTPConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CallsHandler implements Constants, HTTPConnection {

    public String list_folder_root() {

        //stabilisce la connessione
        HttpURLConnection connection = getConnection(LIST_FOLDER_PATH);

        String jBody = """
                {
                    "path": "",
                    "recursive": false,
                    "include_media_info": false,
                    "include_deleted": false,
                    "include_has_explicit_shared_members": false,
                    "include_mounted_folders": true,
                    "include_non_downloadable_files": true
                }
                """;

        try {
            OutputStream OS = connection.getOutputStream();
            byte[] input = jBody.getBytes(StandardCharsets.UTF_8);
            OS.write(input, 0, input.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "", data = "";
        try {
            InputStream IS = connection.getInputStream();
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));

            while ((line = BR.readLine()) != null) {
                data += line;
            }

            IS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public String get_metadata(String path) {
        //stabilisce la connessione
        HttpURLConnection connection = getConnection(GET_METADATA_PATH);

        String jBody = "{\n" +
                "    \"path\": \"" + path + "\",\n" +
                "    \"include_media_info\": true,\n" +
                "    \"include_deleted\": false,\n" +
                "    \"include_has_explicit_shared_members\": false\n" +
                "}";

        try {
            OutputStream OS = connection.getOutputStream();
            byte[] input = jBody.getBytes(StandardCharsets.UTF_8);
            OS.write(input, 0, input.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "", data = "";
        try {
            InputStream IS = connection.getInputStream();
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));

            while ((line = BR.readLine()) != null) {
                data += line;
            }

            IS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



    @Override
    public HttpURLConnection getConnection(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
            connection.setRequestProperty("Authorization", "Bearer " + APP_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
