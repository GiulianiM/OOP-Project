package it.giuugcola.oop.jsonhandler;

/**
 * Classe per la creazione e gestione dei JSON dei file scaricati.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class JSONOfMultimedia implements JSONMetadata {
    /**
     * Percorso del file.
     */
    private String pathDisplay;

    /**
     * Rev univoco del file.
     */
    private String rev;

    /**
     * Dimensione in byte del file.
     */
    private long size;

    /**
     * Ultima modifica da parte del server sul file.
     */
    private String serverModified;

    /**
     * Percorso lowercase del file.
     */
    private String pathLower;

    /**
     * True se il file è scaricabile, viceversa se false.
     */
    private boolean isDownloadable;

    /**
     * Nome del file.
     */
    private String name;

    /**
     * Id univoco del file.
     */
    private String id;

    /**
     * Codice univoco del file.
     */
    private String contentHash;

    /**
     * Ultima modifica da parte del client sul file.
     */
    private String clientModified;

    /**
     * Tag del file.
     */
    private String tag;

    /**
     * Larghezza del file in pixel.
     */
    private long width;

    /**
     * Altezza del file in pixel.
     */
    private long height;

    /**
     * Costruttore di JSONMultimedia
     *
     * @param pathDisplay    percorso file.
     * @param rev            Rev del file.
     * @param size           Dimensione del file in byte.
     * @param serverModified Ultima modifica del file sul server.
     * @param pathLower      Percorso lowercase del file.
     * @param isDownloadable Se è scaricabile.
     * @param name           Nome del file.
     * @param id             Id del file.
     * @param contentHash    Codice univoco del file.
     * @param clientModified Ultima modifica del file dal client.
     * @param tag            Tag del file.
     * @param width          Larghezza in pixel.
     * @param height         Altezza in pixel.
     */
    public JSONOfMultimedia(String pathDisplay, String rev, long size, String serverModified, String pathLower,
                            boolean isDownloadable, String name, String id, String contentHash,
                            String clientModified, String tag, long width, long height) {
        this.pathDisplay = pathDisplay;
        this.rev = rev;
        this.size = size;
        this.serverModified = serverModified;
        this.pathLower = pathLower;
        this.isDownloadable = isDownloadable;
        this.name = name;
        this.id = id;
        this.contentHash = contentHash;
        this.clientModified = clientModified;
        this.tag = tag;
        this.width = width;
        this.height = height;
    }

    /**
     * Costruttore di JSONMultimedia. Crea un oggetto JSONMultimedia con i parametri nulli.
     */
    public JSONOfMultimedia() {
    }

    public String getPathDisplay() {
        return pathDisplay;
    }

    /**
     * Getter del rev del file.
     *
     * @return rev del file.
     */
    public String getRev() {
        return rev;
    }

    /**
     * Getter della dimensione del file.
     *
     * @return size del file.
     */
    public long getSize() {
        return size;
    }

    /**
     * Getter dell'ultima modifica sul server.
     *
     * @return serverModified del file.
     */
    public String getServerModified() {
        return serverModified;
    }

    public String getPathLower() {
        return pathLower;
    }

    /**
     * Getter di isDownloadable.
     *
     * @return isDownloadable del file.
     */
    public boolean IsDownloadable() {
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
     * Getter di contentHash.
     *
     * @return contentHash del file.
     */
    public String getContentHash() {
        return contentHash;
    }

    /**
     * Getter dell'ultima modifica del client.
     *
     * @return getClientModified del file.
     */
    public String getClientModified() {
        return clientModified;
    }

    /**
     * Getter di width.
     *
     * @return width del file.
     */
    public Long getWidth() {
        return width;
    }

    /**
     * Getter di height.
     *
     * @return height del file.
     */
    public Long getHeight() {
        return height;
    }

    /**
     * Setter di pathDisplay.
     *
     * @param pathDisplay Percorso del file
     */
    public void setPathDisplay(String pathDisplay) {
        this.pathDisplay = pathDisplay;
    }

    /**
     * Setter di rev.
     *
     * @param rev rev del file
     */
    public void setRev(String rev) {
        this.rev = rev;
    }

    /**
     * Setter di size.
     *
     * @param size dimensione del file in byte
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Setter di serverModified.
     *
     * @param serverModified serverModified del file
     */
    public void setServerModified(String serverModified) {
        this.serverModified = serverModified;
    }

    /**
     * Setter di pathLower.
     *
     * @param pathLower pathLower del file
     */
    public void setPathLower(String pathLower) {
        this.pathLower = pathLower;
    }

    /**
     * Setter di isDownloadable.
     *
     * @param downloadable isDownloadable del file
     */
    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    /**
     * Setter del nome.
     *
     * @param name nome del file
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter dell'id.
     *
     * @param id id del file
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter di contentHash.
     *
     * @param contentHash contentHash del file
     */
    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    /**
     * Setter di clientModified.
     *
     * @param clientModified clientModified del file
     */
    public void setClientModified(String clientModified) {
        this.clientModified = clientModified;
    }

    /**
     * Setter di tag.
     *
     * @param tag tag del file
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Setter di width.
     *
     * @param width larghezza del file in pixel
     */
    public void setWidth(Long width) {
        this.width = width;
    }

    /**
     * Setter di height.
     *
     * @param height altezza del file in pixel
     */
    public void setHeight(Long height) {
        this.height = height;
    }
}
