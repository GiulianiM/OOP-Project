package it.giuugcola.OOPProject.metaData;

import it.giuugcola.OOPProject.JSONManage.JSONHandler;

import java.util.ArrayList;

public class DownloadedContent {
    ArrayList<MultiMedia> multimedia = new ArrayList<>();
    ArrayList<Folder> folders = new ArrayList<>();

    public void addMultimedia(Object result) {
        ArrayList<Object> json_key_value = JSONHandler.getJsonOfMultimedia(result);

        if (!isAlreadyDownloaded((String) json_key_value.get(5)))
            if (json_key_value.get(0).equals("file"))
                multimedia.add(new File(
                        (String) json_key_value.get(1),
                        (String) json_key_value.get(2),
                        (Long) json_key_value.get(3),
                        (String) json_key_value.get(4),
                        (String) json_key_value.get(5),
                        (Boolean) json_key_value.get(6))
                );
            else
                multimedia.add(new Media(
                        (String) json_key_value.get(0),
                        (String) json_key_value.get(1),
                        (String) json_key_value.get(2),
                        (Long) json_key_value.get(3),
                        (String) json_key_value.get(4),
                        (String) json_key_value.get(5),
                        (Boolean) json_key_value.get(6),
                        (Integer) json_key_value.get(7),
                        (Integer) json_key_value.get(8))
                );


    }

    public void addFolder(Object result) {
        ArrayList<Object> json_key_value = JSONHandler.getJsonOfFolders(result);

        if (!isAlreadyDownloaded((String) json_key_value.get(0), true))
            folders.add(new Folder(
                    (String) json_key_value.get(0),
                    (String) json_key_value.get(1),
                    (String) json_key_value.get(2),
                    (String) json_key_value.get(3)
            ));
    }


    private boolean isAlreadyDownloaded(String content_hash) {
        boolean isContained = false;
        for (MultiMedia m : multimedia)
            if (m.getContent_hash().equals(content_hash)) {
                isContained = true;
                break;
            }
        return isContained;
    }

    private boolean isAlreadyDownloaded(String id, boolean isFolder) {
        boolean isContained = false;
        for (Folder f : folders)
            if (f.getId().equals(id)) {
                isContained = true;
                break;
            }
        return isContained;
    }

    public ArrayList<MultiMedia> getMultimedia() {
        return multimedia;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }
}
