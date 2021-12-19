package it.giuugcola.oop.metadata;

import it.giuugcola.oop.exceptions.DownloadException;
import it.giuugcola.oop.exceptions.ParsingToJsonException;
import it.giuugcola.oop.jsonhandler.JSONHandler;
import it.giuugcola.oop.jsonhandler.JSONOfFolder;
import it.giuugcola.oop.jsonhandler.JSONOfMultimedia;

import java.util.ArrayList;

/**
 * Classe per la gestione dei file e delle cartelle scaricate.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class Downloaded {
    /**
     * Arraylist contenente i file scaricati convertiti in oggetti {@link MultiMedia}.
     */
    private ArrayList<MultiMedia> multimedia = new ArrayList<>();

    /**
     * Arraylist contenente le cartelle scaricate convertite in oggetti {@link Folder}.
     */
    private ArrayList<Folder> folders = new ArrayList<>();

    /**
     * Metodo per l'aggiunta di oggetti {@link MultiMedia} all'arraylist dell'oggetto.
     *
     * @param result Oggetto Multimedia da aggiungere.
     * @return Esito dell'aggiunta.
     */
    public boolean addMultimedia(Object result) throws ParsingToJsonException, DownloadException {
        JSONOfMultimedia jMultimedia = new JSONOfMultimedia();
        JSONHandler.setJSONOfMultimedia(result, jMultimedia);

        if (this.multimedia.isEmpty() || !isMediaDownloaded(jMultimedia.getContentHash())) {
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

            return true;
        }else
            throw new DownloadException("Download NON eseguito! Esiste gia uno stesso file scaricato!");
    }

    /**
     * Metodo per l'aggiunta di oggetti {@link Folder} all'arraylist dell'oggetto.
     *
     * @param result Oggetto Folder da aggiungere.
     * @return Esito dell'aggiunta.
     */
    public boolean addFolder(Object result) throws ParsingToJsonException, DownloadException {
        JSONOfFolder jFolder = new JSONOfFolder();
        JSONHandler.setJSONOfFolder(result, jFolder);

        if (this.folders.isEmpty() || !isFolderDownloaded(jFolder.getId())) {
            folders.add(new Folder(
                    jFolder.getPathLower(),
                    jFolder.getName(),
                    jFolder.getTag(),
                    jFolder.getId()
            ));
            return true;
        }else
            throw new DownloadException("Download NON eseguito! Esiste gia uno stesso zip scaricato!");
    }

    /**
     * Metodo ausiliario di {@link #addMultimedia(Object)}, per verificare se il file sia già stato scaricato.
     *
     * @param contentHash Hash univoco del file.
     * @return Esito del confronto.
     */
    private boolean isMediaDownloaded(String contentHash) {
        for (MultiMedia m : this.multimedia)
            if (m.getContentHash().equals(contentHash))
                return true;
        return false;
    }

    /**
     * Metodo ausiliario di {@link #addFolder(Object)}, per verificare se la cartella sia già stata scaricata.
     *
     * @param id Id univoco del file.
     * @return Esito del confronto.
     */
    private boolean isFolderDownloaded(String id) {
        for (Folder f : this.folders)
            if (f.getId().equals(id))
                return true;
        return false;
    }

    /**
     * Getter dell'arraylist di {@link MultiMedia} di {@link Downloaded}
     *
     * @return Arraylist di Multimedia dell'oggetto.
     */
    public ArrayList<MultiMedia> getMultimedia() {
        return multimedia;
    }

    /**
     * Getter dell'arraylist di {@link Folder} di {@link Downloaded}
     *
     * @return Arraylist di Folder dell'oggetto.
     */
    public ArrayList<Folder> getFolders() {
        return folders;
    }

    /**
     * Metodo ausiliario per controllare se esista almeno un file scaricato del tipo specificato.
     *
     * @param tag Tipo di file.
     * @return Esito del controllo.
     */
    public boolean isAtLeastOne(String tag) {
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag))
                return true;
        return false;
    }

    /**
     * Metodo ausiliario per ottenere la dimensione massimo dei file scaricati in base al tipo.
     *
     * @param tag Tipo di file.
     * @return Dimensione del file più grande.
     */
    public Double getMaxSize(String tag) {
        double size = 0d;
        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSizeMB() > size) {
                size = m.getSizeMB();
            }

        return size;
    }

    /**
     * Metodo ausiliario per ottenere la dimensione minima dei file scaricati in base al tipo.
     *
     * @param tag Tipo di file.
     * @return Dimensione del file più piccolo.
     */
    public Double getMinSize(String tag) {
        double size = 0;
        for (MultiMedia m : multimedia) {
            if (m.getTag().equals(tag)) {
                size = m.getSizeMB();
                break;
            }
        }

        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSizeMB() < size)
                size = m.getSizeMB();

        return size;
    }

    /**
     * Metodo ausiliario per ottenere la dimensione media dei file scaricati in base al tipo.
     *
     * @param tag Tipo di file.
     * @return Dimensione media dei file.
     */
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

    /**
     * Metodo ausiliario per ottenere il nome del file più grande in base al tipo.
     *
     * @param tag Tipo del file.
     * @return Nome del file.
     */
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

    /**
     * Metodo ausiliario per ottenere il nome del file più piccolo in base al tipo.
     *
     * @param tag Tipo del file.
     * @return Nome del file.
     */
    public String getMinSizeName(String tag) {
        String name = null;
        long size = 0;

        for (MultiMedia m : multimedia) {
            if (m.getTag().equals(tag)) {
                size = m.getSize();
                break;
            }
        }

        for (MultiMedia m : multimedia)
            if (m.getTag().equals(tag) && m.getSize() <= size) {
                size = m.getSize();
                name = m.getName();
            }

        return name;
    }
}
