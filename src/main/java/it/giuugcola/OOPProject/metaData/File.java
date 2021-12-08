package it.giuugcola.OOPProject.metaData;

/**
 * Classe figlio di MultiMedia
 * <p>
 * Per conoscere i vari metodi {@link MultiMedia}
 * </p>
 *
 * @author Davide Colabella
 * @implNote
 * @see MultiMedia
 * @since 1.0
 */

public class File extends MultiMedia {

    /**
     * Costruttore della classe {@link File}, per tipologie di file quali documenti o file di testo.
     * <p>
     * Richiama il costruttore della classe padre {@link MultiMedia}.
     * </p>
     * @param name Nome del file.
     * @param size Dimensione del file.
     * @param content_hash
     * @param id
     * @param is_downloadable
     */
    public File(String rev, long size, String path_lower, boolean is_downloadable, String name, String tag, String id, String content_hash) {
        super(rev, size, path_lower, is_downloadable, name, tag, id, content_hash);
    }
}
