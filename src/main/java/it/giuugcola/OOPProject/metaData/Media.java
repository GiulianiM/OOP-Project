package it.giuugcola.OOPProject.metaData;

/**
 * Classe figlio di {@code MultiMedia}, esempi di come si può utilizzare {@code Media}:
 * <blockquote><pre>
 *     Media fileImg =
 *     new Media("parrots.img", "/Images", 7200, 1300, 500);
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
 * La classe Media contiene anche metodi basilari quali i getter per ogni suo parametro:
 * {@code name, path, size}; un metodo toString per la stampa a schermo
 * delle caratteristiche del file.
 *
 * @author Davide Colabella
 * @implNote
 * @see MultiMedia
 * @see java.lang.Integer
 * @since 1.0
 */

public class Media extends MultiMedia {

    private final int width;
    private final int height;
    private final String type;

    /**
     * Costruttore della classe {@link Media}, per tipologie di file quali immagini o video.
     * <p>
     * Eredita il costruttore super dalla classe {@link MultiMedia}
     * </p>
     * @param name   Nome del file.
     * @param path   Percorso del file su Dropbox.
     * @param size   Dimensione del file.
     * @param width  Larghezza file, in px.
     * @param height Altezza file, in px.
     * @param is_downloadable
     * @param id
     * @param content_hash
     * @param type
     */
    public Media(String type, String name, String path, long size, String id, String content_hash, boolean is_downloadable, int width, int height) {
        super(name, path, content_hash, size, id, is_downloadable);
        this.width = width;
        this.height = height;
        this.type = type;
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

    public String getType() {
        return type;
    }

    /**
     * Override del metodo toString adattato alla classe {@link Media}
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
