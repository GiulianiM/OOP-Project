package it.giuugcola.OOPProject.restController;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import it.giuugcola.OOPProject.settings.Constants;
import it.giuugcola.OOPProject.settings.DropboxClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CallsHandler implements Constants {

    public String list_folder_root() {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(LIST_FOLDER_DROPBOX_API_PATH).openConnection();

            connection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
            connection.setRequestProperty("Authorization", "Bearer " + APP_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        String line;
        StringBuilder data = new StringBuilder();
        try {
            InputStream IS = connection.getInputStream();
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));

            while ((line = BR.readLine()) != null) {
                data.append(line);
            }

            IS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toString();
    }

    public String get_metadata(String path) {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(GET_METADATA_DROPBOX_API_PATH).openConnection();

            connection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
            connection.setRequestProperty("Authorization", "Bearer " + APP_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        String line;
        StringBuilder data = new StringBuilder();
        try {
            InputStream IS = connection.getInputStream();
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));

            while ((line = BR.readLine()) != null) {
                data.append(line);
            }

            IS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    public FileMetadata download(String path) {
        String download_where = DOWNLOAD_FOLDER_PATH + "/" + getFileOrFolderName(path);

        FileMetadata metadata = null;
        try {
            OutputStream outputStream  = new FileOutputStream(download_where);
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

    public DownloadZipResult download_zip(String path) {
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

    //Esempio input: /Images/AboutYesterday/Animals
    //Return expected: Animals
    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private String getFileOrFolderName(String path) {
        return path.substring( path.lastIndexOf("/") + 1);
    }
}
