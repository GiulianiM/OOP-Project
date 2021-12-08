package it.giuugcola.OOPProject.restController;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import it.giuugcola.OOPProject.settings.Constants;
import it.giuugcola.OOPProject.settings.DropboxClient;

import java.io.*;

public class CallsHandler implements Constants {

    public static ListFolderResult list_folder_root() {
        ListFolderResult metadata = null;

        try{
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

        try{
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


    //Esempio input: /Images/AboutYesterday/Animals/dog.jpg
    //Return expected: dog.jpg
    private static String getFileOrFolderName(String path) {
        return path.substring( path.lastIndexOf("/") + 1);
    }
}
