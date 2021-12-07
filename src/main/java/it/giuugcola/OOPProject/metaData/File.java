package it.giuugcola.OOPProject.metaData;

/**
 * Classe astratta, esempi di come si può utilizzare {@code File}:
 * <blockquote><pre>
 *     File fileTxt = new File("sample.txt", "/Documents", 7200);
 *     System.out.println(fileTxt)
 *     fileTxt.getName();
 *     fileTxt.getPath();
 *     fileTxt.getSize();
 * </pre></blockquote>
 * La classe File contiene anche metodi basilari quali i getter per ogni suo parametro:
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


public abstract class File {

    /**
     * Stringa utilizzata per immagazzinare il nome del file.
     */
    private final String name;

    /**
     * Stringa utilizzata per immagazzinare il percorso del file.
     */
    private final String path;

    /**
     * Valore utilizzato per contenere la dimensione del file in byte.
     */
    private final long size;

    /**
     * Costruttore della classe file, per tipologie di file quali documenti o file di testo.
     *
     * @param name Nome del file.
     * @param path Percorso del file su Dropbox.
     * @param size Dimensione del file.
     */
    public File(String name, String path, long size) {
        if (name == null) {
            throw new IllegalArgumentException("Required value for 'name' is null");
        }
        this.name = name;
        this.path = path;
        this.size = size;
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

    /**
     * Override del metodo toString adattato alla classe {@link File}
     *
     * @return File{name="", path="/", size=}
     */
    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                '}';
    }

}
