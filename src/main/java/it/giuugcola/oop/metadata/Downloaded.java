package it.giuugcola.oop.metadata;

import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.jsonhandler.JSONOfFolder;
import it.giuugcola.oop.jsonhandler.JSONOfMultimedia;

import java.util.ArrayList;

public class Downloaded {
    ArrayList<MultiMedia> multimedia;
    ArrayList<Folder> folders = new ArrayList<>();

    public Downloaded(ArrayList<MultiMedia> multimedia) {
        this.multimedia = new ArrayList<>(multimedia);
    }

    public Downloaded() {
        this.multimedia = new ArrayList<>();
    }

    public void addMultimedia(Object result) {
        JSONOfMultimedia jMultimedia = new JSONOfMultimedia();
        JSONHandler.setJSONOfMultimedia(result, jMultimedia);

        if (!isMediaDownloaded(jMultimedia.getContentHash())) {
            if (jMultimedia.getTag().equals("file")) {
                multimedia.add(new File(
                        jMultimedia.getRev(),
                        jMultimedia.getSize(),
                        jMultimedia.getPathLower(),
                        jMultimedia.IsDownloadable(),
                        jMultimedia.getName(),
                        jMultimedia.getTag(),
                        jMultimedia.getId(),
                        jMultimedia.getContentHash()
                ));
            }else{
                multimedia.add(new Media(
                        jMultimedia.getRev(),
                        jMultimedia.getSize(),
                        jMultimedia.getPathLower(),
                        jMultimedia.IsDownloadable(),
                        jMultimedia.getName(),
                        jMultimedia.getTag(),
                        jMultimedia.getId(),
                        jMultimedia.getContentHash(),
                        jMultimedia.getWidth(),
                        jMultimedia.getHeight()
                ));
            }
        }
    }

    public void addFolder(Object result) {
        JSONOfFolder jFolder = new JSONOfFolder();
        JSONHandler.setJSONOfFolder(result, jFolder);

        if (!isFolderDownloaded(jFolder.getId()))
            folders.add(new Folder(
                    jFolder.getPathLower(),
                    jFolder.getName(),
                    jFolder.getTag(),
                    jFolder.getId()
            ));
    }


    private boolean isMediaDownloaded(String contentHash) {
        for (MultiMedia m : this.multimedia)
            if (m.getContentHash().equals(contentHash))
                return true;
        return false;
    }
    private boolean isFolderDownloaded(String id) {
        for (Folder f : this.folders)
            if (f.getId().equals(id))
                return true;
        return false;
    }

    public ArrayList<MultiMedia> getMultimedia() {
        return multimedia;
    }
    public ArrayList<Folder> getFolders() {
        return folders;
    }
}
