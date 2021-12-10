package it.giuugcola.OOPProject.metaData;

import it.giuugcola.OOPProject.JSONManage.JSONHandler;
import it.giuugcola.OOPProject.JSONManage.JSONOfFolder;
import it.giuugcola.OOPProject.JSONManage.JSONOfMultimedia;

import java.util.ArrayList;

public class DownloadedContent {
    ArrayList<MultiMedia> multimedia;
    ArrayList<Folder> folders = new ArrayList<>();

    public DownloadedContent(ArrayList<MultiMedia> multimedia) {
        this.multimedia = new ArrayList<>(multimedia);
    }

    public DownloadedContent() {
        this.multimedia = new ArrayList<>();
    }

    public void addMultimedia(Object result) {
        JSONHandler.populateClassJSONOfMultimedia(result);

        if (isNotDownloaded(JSONOfMultimedia.getContent_hash(), true))
            if (JSONOfMultimedia.getTag().equals("file"))
                multimedia.add(new File(
                        JSONOfMultimedia.getRev(),
                        JSONOfMultimedia.getSize(),
                        JSONOfMultimedia.getPath_lower(),
                        JSONOfMultimedia.getIs_downloadable(),
                        JSONOfMultimedia.getName(),
                        JSONOfMultimedia.getTag(),
                        JSONOfMultimedia.getId(),
                        JSONOfMultimedia.getContent_hash()
                ));
            else
                multimedia.add(new Media(
                        JSONOfMultimedia.getRev(),
                        JSONOfMultimedia.getSize(),
                        JSONOfMultimedia.getPath_lower(),
                        JSONOfMultimedia.getIs_downloadable(),
                        JSONOfMultimedia.getName(),
                        JSONOfMultimedia.getTag(),
                        JSONOfMultimedia.getId(),
                        JSONOfMultimedia.getContent_hash(),
                        JSONOfMultimedia.getWidth(),
                        JSONOfMultimedia.getHeight()
                ));
    }

    public void addFolder(Object result) {
        JSONHandler.populateClassJSONOfFolder(result);

        if (isNotDownloaded(JSONOfFolder.getId()))
            folders.add(new Folder(
                    JSONOfFolder.getPath_lower(),
                    JSONOfFolder.getName(),
                    JSONOfFolder.getTag(),
                    JSONOfFolder.getId()
            ));
    }


    private boolean isNotDownloaded(String unique, boolean isMultimedia) {
        boolean isContained = false;

        for (MultiMedia m : multimedia)
            if (m.getContent_hash().equals(unique)) {
                isContained = true;
                break;
            }

        return !isContained;
    }

    private boolean isNotDownloaded(String unique) {
        boolean isContained = false;

        for (Folder f : folders)
            if (f.getId().equals(unique)) {
                isContained = true;
                break;
            }
        return !isContained;
    }

    public ArrayList<MultiMedia> getMultimedia() {
        return multimedia;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }
}
