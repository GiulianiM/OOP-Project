package it.giuugcola.oop.settings;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import it.giuugcola.oop.exceptions.DropboxExceptions;

/**
 * Classe per la gestione della connessione all'API di Dropbox.
 */
public class DropboxClient {

    /**
     * Nome dell'app registrato su Dropbox Developers.
     */
    private final String clientIdentifier = "dropbox/Progetto-OOP";

    /**
     * Token bearer per l'accesso al dropbox.
     */
    private final String APP_TOKEN = "RrwYr735p88AAAAAAAAAAfVC-Wr-r0bG-JydWI9l5xJY2onu0wcJStqFrD895QWv";

    /**
     * Oggetto {@link DbxClientV2} per la connessione al client.
     */
    public static DbxClientV2 client;

    /**
     * Metodo che stabilisce la connessione al Dropbox.
     * @throws DropboxExceptions Se il token o il client identifier sono errati.
     */
    public DropboxClient() throws DropboxExceptions {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(clientIdentifier).build();
        client = new DbxClientV2(config, APP_TOKEN);

        try {
            client.users().getCurrentAccount();
        } catch (DbxException e){
            throw new DropboxExceptions("Parametri \"APP_TOKEN\" e/o \"clientIdentifier\" incorretti");
        }
    }

}
