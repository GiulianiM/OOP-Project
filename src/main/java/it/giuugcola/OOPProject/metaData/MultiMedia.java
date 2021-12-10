package it.giuugcola.OOPProject.metaData;

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
public abstract class MultiMedia {

    private final String rev;
    private final long size;
    private final String path_lower;
    private final boolean is_downloadable;
    private final String name;
    private final String tag;
    private final String id;
    private final String content_hash;

    public MultiMedia(String rev, long size, String path_lower, boolean is_downloadable, String name, String tag, String id, String content_hash) {
        this.rev = rev;
        this.size = size;
        this.path_lower = path_lower;
        this.is_downloadable = is_downloadable;
        this.name = name;
        this.tag = tag;
        this.id = id;
        this.content_hash = content_hash;
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

    public String getPath_lower() {
        return path_lower;
    }

    public boolean isDownloadable() {
        return is_downloadable;
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

    public String getContent_hash() {
        return content_hash;
    }


    @Override
    public String toString() {
        return "File{" +
                ", rev='" + rev + '\'' +
                ", size=" + size +
                ", path_lower='" + path_lower + '\'' +
                ", is_downloadable=" + is_downloadable +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                ", content_hash='" + content_hash + '\'' +
                '}';
    }
}
