package it.giuugcola.oop.metadata;

/**
 * Classe figlio di {@code MultiMedia}, esempi di come si può utilizzare {@code Media}:
 * <blockquote><pre>
 *     Media fileImg =
 *     new Media("parrots.img", "/Images", 7200, 1300, 500);
 *     System.out.prlongln(fileImg)
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
 * @see java.lang
 * @since 1.0
 */

public class Media extends MultiMedia {

    private final long width;
    private final long height;

    /**
     * Costruttore della classe {@link Media}, per tipologie di file quali immagini o video.
     * <p>
     * Eredita il costruttore super dalla classe {@link MultiMedia}
     * </p>
     *
     * @param name            Nome del file.
     * @param size            Dimensione del file.
     * @param width           Larghezza file, in px.
     * @param height          Altezza file, in px.
     * @param isDownloadable
     * @param id
     * @param contentHash
     */
    public Media(String rev, long size, String pathLower, boolean isDownloadable, String name, String tag, String id, String contentHash, long width, long height) {
        super(rev, size, pathLower, isDownloadable, name, tag, id, contentHash);
        this.width = width;
        this.height = height;
    }

    /**
     * Metodo get per ottenere la Larghezza di un file.
     *
     * @return Larghezza file in px
     */
    public long getWidth() {
        return width;
    }

    /**
     * Metodo get per ottenere l'Altezza di un file.
     *
     * @return Altezza file in px
     */
    public long getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Media{" +
                ", rev='" + getRev() + '\'' +
                ", size=" + getSize() + '\'' +
                ", path_lower='" + getPathLower() + '\'' +
                ", is_downloadable=" + isDownloadable() + '\'' +
                ", name='" + getName() + '\'' +
                ", tag='" + getTag() + '\'' +
                ", id='" + getId() + '\'' +
                ", content_hash='" + getContentHash() + '\'' +
                "width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
