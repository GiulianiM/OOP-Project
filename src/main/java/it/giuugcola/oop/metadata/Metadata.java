package it.giuugcola.oop.metadata;

public interface Metadata {
    /**
     * Getter del percorso lowercase.
     * @return Percorso lowercase
     */
    String getPathLower();

    /**
     * Getter del nome.
     * @return Nome
     */
    String getName();

    /**
     * Getter del tag.
     * @return Tag
     */
    String getTag();

    /**
     * Getter dell'id.
     * @return Id
     */
    String getId();

    /**
     * Metodo toString
     * @return Stringa formattata con gli attributi dell'oggetto.
     */
    String toString();
}
