package it.giuugcola.oop.metadata;

/**
 * Classe astratta, esempi di come si può utilizzare {@code MultiMedia}:
 * <blockquote><pre>
 *     MultiMedia fileTxt =
 *     new MultiMedia("sample.txt", "/Documents", 7200);
 *     System.out.println(fileTxt)
 *     fileTxt.getName();
 *     fileTxt.getPath();
 *     fileTxt.getSize();
 * </pre></blockquote>
 * La classe MultiMedia contiene anche metodi basilari quali i getter per ogni suo parametro:
 * {@code name, path, size}; un metodo toString per la stampa a schermo
 * delle caratteristiche del file.
 *
 * @author Davide Colabella
 * @implNote
 * @see java.lang.String
 * @see java.lang.Integer
 * @see java.lang.Long
 * @since 1.0
 */
public abstract class MultiMedia implements Metadata {

    private final String rev;
    private final long size;
    private final String pathLower;
    private final boolean isDownloadable;
    private final String name;
    private final String tag;
    private final String id;
    private final String contentHash;

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


    public String getRev() {
        return rev;
    }

    public long getSize() {
        return size;
    }

    public Double getSizeMB() {
        double sizeMB = size / (double) 1048576;
        return Math.round(sizeMB * 100.0) / 100.0;
    }

    public String getPathLower() {
        return pathLower;
    }

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
