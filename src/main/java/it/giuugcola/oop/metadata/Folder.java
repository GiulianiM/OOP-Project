package it.giuugcola.oop.metadata;

/**
 * Classe per la gestione delle cartelle scaricate.
 * @author Davide Colabella
 * @author Matteo Giuliani
 * @see Metadata
 */
public class Folder implements Metadata {
    /**
     * Percorso lowercase della cartella.
     */
    private final String pathLower;

    /**
     * Nome della cartella.
     */
    private final String name;

    /**
     * Tag della cartella.
     */
    private final String tag;

    /**
     * Id univoco della cartella.
     */
    private final String id;

    /**
     * Costruttore dell'oggetto {@link Folder}
     * @param pathLower Percorso lowercase della cartella.
     * @param name Nome della cartella.
     * @param tag Tag della cartella.
     * @param id Id univoco della cartella.
     */
    public Folder(String pathLower, String name, String tag, String id) {
        this.pathLower = pathLower;
        this.name = name;
        this.tag = tag;
        this.id = id;
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

    @Override
    public String toString() {
        return "Folder{" +
                ", path_lower='" + pathLower + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
