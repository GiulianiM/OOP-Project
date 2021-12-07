package it.giuugcola.OOPProject.metaData;

/**
 * Classe figlia di {@code File}, esempi di come si può utilizzare {@code FileMultimedia}:
 * <blockquote><pre>
 *     FileMultimedia fileImg =
 *     new File("parrots.img", "/Images", 7200, 1300, 500);
 *     System.out.println(fileImg)
 *     fileImg.getWidth();
 *     fileImg.getHeight();
 * </pre></blockquote>
 * Possiamo anche utilizzare i metodi ereditati quali:
 * <blockquote><pre>
 *     fileImg.getName();
 *     fileImg.getPath();
 *     fileImg.getSize();
 * </pre></blockquote>
 * <p>
 * La classe File contiene anche metodi basilari quali i getter per ogni suo parametro:
 * {@code name, path, size}; un metodo toString per la stampa a schermo
 * delle caratteristiche del file.
 *
 * @author Davide Colabella
 * @implNote
 * @see it.giuugcola.OOPProject.metaData.File
 * @see java.lang.Integer
 * @since 1.0
 */

public class FileMultimedia extends File {

    /**
     * Valore utilizzato per contenere la larghezza del file.
     */
    private final int width; //da rivedere se usare int per width e height

    /**
     * Valore utilizzato per contenere l'altezza del file.
     */
    private final int height;

    /**
     * Costruttore della classe {@link FileMultimedia}, per tipologie di file quali immagini o video.
     * <p>
     * Eredita il costruttore super dalla classe {@link File}
     * </p>
     *
     * @param name   Nome del file.
     * @param path   Percorso del file su Dropbox.
     * @param size   Dimensione del file.
     * @param height Altezza file, in px.
     * @param width  Larghezza file, in px.
     */
    public FileMultimedia(String name, String path, long size, int width, int height) {
        super(name, path, size);
        this.width = width;
        this.height = height;
    }

    /**
     * Metodo get per ottenere la Larghezza di un file.
     *
     * @return Larghezza file in px
     */
    public int getWidth() {
        return width;
    }

    /**
     * Metodo get per ottenere l'Altezza di un file.
     *
     * @return Altezza file in px
     */
    public int getHeight() {
        return height;
    }

    /**
     * Override del metodo toString adattato alla classe {@link FileMultimedia}
     *
     * @return File{name="", path="/", size=, width=, height=}
     */
    @Override
    public String toString() {
        return "File{" +
                "name='" + this.getName() + '\'' +
                ", path='" + this.getPath() + '\'' +
                ", size=" + this.getSize() +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
