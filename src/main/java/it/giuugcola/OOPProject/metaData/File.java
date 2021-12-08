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
     * @param path Percorso del file su Dropbox.
     * @param size Dimensione del file.
     * @param content_hash
     * @param id
     * @param is_downloadable
     */
    public File(String name, String path, long size, String  id,  String content_hash, boolean is_downloadable) {
        super(name, path, content_hash, size, id, is_downloadable);
    }
}
