package it.giuugcola.oop.metadata;

/**
 * Classe figlio di MultiMedia
 * <p>
 * Per conoscere i vari metodi {@link MultiMedia}
 * </p>
 *
 * @author Davide Colabella
 * @author Matteo Giulinai
 * @see MultiMedia
 */
public class File extends MultiMedia {

    /**
     * Costruttore della classe {@link File}, per tipologie di file quali documenti o file di testo.
     * <p>
     * Richiama il costruttore della classe padre {@link MultiMedia}.
     * </p>
     *
     * @param rev Rev univoco del file.
     * @param size Dimensione in byte del file.
     * @param pathLower Percorso lowercase del file.
     * @param isDownloadable Se il file è scaricabile.
     * @param name Nome del file.
     * @param tag Tag del file.
     * @param id Id univoco del file.
     * @param contentHash Hash univoco del file.
     */
    public File(String rev, long size, String pathLower, boolean isDownloadable, String name, String tag, String id, String contentHash) {
        super(rev, size, pathLower, isDownloadable, name, tag, id, contentHash);
    }
}
