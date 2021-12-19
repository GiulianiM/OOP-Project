package it.giuugcola.oop.metadata;

/**
 * Classe figlio di {@code MultiMedia}, specifica per i file multimediali quali video e foto.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 * @see MultiMedia
 */
public class Media extends MultiMedia {

    /**
     * Larghezza immagine/video in pixel.
     */
    private final long width;

    /**
     * Altezza immagine/video in pixel.
     */
    private final long height;

    /**
     * Costruttore della classe {@link Media}, per tipologie di file quali immagini o video.
     * <p>
     * Eredita il costruttore super dalla classe {@link MultiMedia}
     * </p>
     *
     * @param name           Nome del file.
     * @param size           Dimensione del file.
     * @param width          Larghezza file, in px.
     * @param height         Altezza file, in px.
     * @param isDownloadable Se il file può essere scaricato.
     * @param id             Id univoco del file.
     * @param contentHash    Hash univoco del file.
     */
    public Media(String rev, long size, String pathLower, boolean isDownloadable, String name, String tag, String id, String contentHash, long width, long height) {
        super(rev, size, pathLower, isDownloadable, name, tag, id, contentHash);
        this.width = width;
        this.height = height;
    }

    /**
     * Getter per ottenere la Larghezza di un file.
     *
     * @return Larghezza file in px
     */
    public long getWidth() {
        return width;
    }

    /**
     * Getter per ottenere l'Altezza di un file.
     *
     * @return Altezza file in px
     */
    public long getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Media{" + ", rev='" + getRev() + '\'' + ", size=" + getSize() + '\'' + ", path_lower='" + getPathLower() + '\'' + ", is_downloadable=" + isDownloadable() + '\'' + ", name='" + getName() + '\'' + ", tag='" + getTag() + '\'' + ", id='" + getId() + '\'' + ", content_hash='" + getContentHash() + '\'' + "width='" + width + '\'' + ", height='" + height + '\'' + '}';
    }
}
