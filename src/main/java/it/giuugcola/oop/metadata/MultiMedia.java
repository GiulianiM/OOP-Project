package it.giuugcola.oop.metadata;

/**
 * Classe per la gestione dei file multimediali scaricati.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class MultiMedia implements Metadata {

    /**
     * Rev univoco del file.
     */
    private final String rev;

    /**
     * Dimensione del file in byte.
     */
    private final long size;

    /**
     * Percorso lowercase del file.
     */
    private final String pathLower;

    /**
     * Se il file è scaricabile.
     */
    private final boolean isDownloadable;

    /**
     * Nome del file.
     */
    private final String name;

    /**
     * Tag del file.
     */
    private final String tag;

    /**
     * Id univoco del file.
     */
    private final String id;

    /**
     * Hash univoco del file.
     */
    private final String contentHash;

    /**
     * Costruttore di {@link MultiMedia}.
     *
     * @param rev            Rev univoco del file.
     * @param size           Dimensione in byte del file.
     * @param pathLower      Percorso lowercase del file.
     * @param isDownloadable Se il file è scaricabile.
     * @param name           Nome del file.
     * @param tag            Tag del file.
     * @param id             Id univoco del file.
     * @param contentHash    Hash univoco del file.
     */
    protected MultiMedia(String rev, long size, String pathLower, boolean isDownloadable, String name, String tag, String id, String contentHash) {
        this.rev = rev;
        this.size = size;
        this.pathLower = pathLower;
        this.isDownloadable = isDownloadable;
        this.name = name;
        this.tag = tag;
        this.id = id;
        this.contentHash = contentHash;
    }

    /**
     * Getter di Rev.
     * @return Rev del file
     */
    public String getRev() {
        return rev;
    }

    /**
     * Getter di size.
     * @return Dimensione del file
     */
    public long getSize() {
        return size;
    }

    /**
     * Metodo ausiliario per convertire la dimensione da byte in MB.
     * @return Size in MB
     */
    public double getSizeMB() {
        double MEGABYTE = 1024 * 1024;
        double sizeMB = size / MEGABYTE;
        return Math.round(sizeMB * 100.0) / 100.0;
    }

    public String getPathLower() {
        return pathLower;
    }

    /**
     * Getter di isDownloadable.
     * @return isDownloadable
     */
    public boolean isDownloadable() {
        return isDownloadable;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getId() {
        return id;
    }

    /**
     * Getter di hash.
     * @return Hash del file.
     */
    public String getContentHash() {
        return contentHash;
    }

    @Override
    public String toString() {
        return "File{" +
                ", rev='" + rev + '\'' +
                ", size=" + size +
                ", path_lower='" + pathLower + '\'' +
                ", is_downloadable=" + isDownloadable +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                ", content_hash='" + contentHash + '\'' +
                '}';
    }
}
