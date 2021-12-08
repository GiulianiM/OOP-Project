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

    private final String name;
    private final String path;
    private final String content_hash;
    private final String id;
    private final long size;
    private final boolean is_downloadable;


    /**
     * Costruttore della classe MultiMedia, per tipologie di file quali documenti o file di testo.
     * @param name Nome del file.
     * @param path Percorso del file su Dropbox.
     * @param content_hash
     * @param size Dimensione del file.
     * @param id
     * @param is_downloadable
     */
    public MultiMedia(String name, String path, String content_hash, long size, String id, boolean is_downloadable) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        this.path = path;
        this.size = size;
        this.id = id;
        this.content_hash = content_hash;
        this.is_downloadable = is_downloadable;
    }

    /**
     * Metodo get per ottenere il nome di un file.
     *
     * @return Nome file
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo get per ottenere il Percorso di un file.
     *
     * @return Percorso file
     */
    public String getPath() {
        return path;
    }

    /**
     * Metodo get per ottenere la Dimensione di un file.
     *
     * @return Dimensione file
     */
    public long getSize() {
        return size;
    }

    public String getContent_hash() {
        return content_hash;
    }

    public String getId() {
        return id;
    }

    public boolean isIs_downloadable() {
        return is_downloadable;
    }

    /**
     * Override del metodo toString adattato alla classe {@link MultiMedia}
     *
     * @return File{name="", path="/", size=}
     */
    @Override
    public String toString() {
        return "MultiMedia{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", content_hash='" + content_hash + '\'' +
                ", id='" + id + '\'' +
                ", size=" + size +
                ", is_downloadable=" + is_downloadable +
                '}';
    }
}
