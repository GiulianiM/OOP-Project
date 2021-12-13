package it.giuugcola.oop.metadata;

import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.jsonhandler.JSONOfFolder;
import it.giuugcola.oop.jsonhandler.JSONOfMultimedia;

import java.util.ArrayList;

public class Downloaded {
    private ArrayList<MultiMedia> multimedia;
    private ArrayList<Folder> folders = new ArrayList<>();

    public Downloaded(ArrayList<MultiMedia> multimedia) {
        this.multimedia = new ArrayList<>(multimedia);
    }

    public Downloaded() {
        this.multimedia = new ArrayList<>();
    }

    public void addMultimedia(Object result) throws ParsingToJsonException {
        JSONOfMultimedia jMultimedia = new JSONOfMultimedia();
        JSONHandler.setJSONOfMultimedia(result, jMultimedia);

        if (this.multimedia.size() == 0 || !isMediaDownloaded(jMultimedia.getContentHash())) {
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
            } else {
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

    public void addFolder(Object result) throws ParsingToJsonException {
        JSONOfFolder jFolder = new JSONOfFolder();
        JSONHandler.setJSONOfFolder(result, jFolder);

        if (this.folders.size() == 0 || !isFolderDownloaded(jFolder.getId()))
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



    public boolean isAtLeastOne(String tag) {
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag))
                return true;
        return false;
    }

    public Double getMaxSize(String tag) {
        double size = 0d;
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSizeMB() > size) {
                size = m.getSizeMB();
            }

        return size;
    }

    public Double getMinSize(String tag) {
        double size = multimedia.get(0).getSizeMB();
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSizeMB() < size)
                size = m.getSizeMB();

        return size;
    }

    public Double getAvgSize(String tag) {
        double size = 0;
        int cont = 0;
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag)) {
                size += m.getSizeMB();
                cont++;
            }

        return size / cont;
    }

    public String getMaxSizeName(String tag) {
        String name = null;
        long size = 0;

        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSize() > size) {
                size = m.getSize();
                name = m.getName();
            }

        return name;
    }

    public String getMinSizeName(String tag) {
        String name = null;
        long size = multimedia.get(0).getSize();

        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSize() <= size) {
                size = m.getSize();
                name = m.getName();
            }

        return name;
    }
}
