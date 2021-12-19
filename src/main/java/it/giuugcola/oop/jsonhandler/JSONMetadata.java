package it.giuugcola.oop.jsonhandler;

public interface JSONMetadata {
    /**
     * Getter del percorso come appare su Dropbox.
     * @return percorso del file.
     */
    String getPathDisplay();

    /**
     * Getter del percorso lowercase.
     * @return percorso lowercase del file.
     */
    String getPathLower();

    /**
     * Getter del nome del file.
     * @return Nome file.
     */
    String getName();

    /**
     * Getter del tag del file.
     * @return Tag del file
     */
    String getTag();

    /**
     * Getter dell'id del file.
     * @return Id del file.
     */
    String getId();
}
