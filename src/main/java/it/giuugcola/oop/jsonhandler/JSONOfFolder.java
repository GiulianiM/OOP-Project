package it.giuugcola.oop.jsonhandler;

/**
 * Classe per la creazione e gestione dei JSON delle cartelle scaricate.
 *
 * @author Davide Colabella
 * @author Matteo Giuliani
 */
public class JSONOfFolder implements JSONMetadata {
    /**
     * Percorso della cartella.
     */
    private String pathDisplay;

    /**
     * Percorso lowercase della cartella.
     */
    private String pathLower;

    /**
     * Nome della cartella.
     */
    private String name;

    /**
     * Tag della cartella.
     */
    private String tag;

    /**
     * Id univoco della cartella.
     */
    private String id;

    /**
     * Costruttore di JSONFolder.
     *
     * @param pathDisplay percorso della cartella.
     * @param pathLower   percorso lowercase della cartella.
     * @param name        nome della cartella.
     * @param tag         tag della cartella.
     * @param id          id della cartella.
     */
    public JSONOfFolder(String pathDisplay, String pathLower, String name, String tag, String id) {
        this.pathDisplay = pathDisplay;
        this.pathLower = pathLower;
        this.name = name;
        this.tag = tag;
        this.id = id;
    }

    /**
     * Costruttore di JSONFolder. Crea un oggetto JSONFolder con i parametri nulli.
     */
    public JSONOfFolder() {
    }

    public String getPathDisplay() {
        return pathDisplay;
    }

    public String getPathLower() {
        return pathLower;
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
     * Setter del path.
     *
     * @param pathDisplay percorso della cartella.
     */
    public void setPathDisplay(String pathDisplay) {
        this.pathDisplay = pathDisplay;
    }

    /**
     * Setter del pathLower.
     *
     * @param pathLower percorso lowercase della cartella.
     */
    public void setPathLower(String pathLower) {
        this.pathLower = pathLower;
    }

    /**
     * Setter del nome.
     *
     * @param name nome della cartella.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter del tag.
     *
     * @param tag tag della cartella.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Setter dell'id.
     *
     * @param id id della cartella.
     */
    public void setId(String id) {
        this.id = id;
    }
}
