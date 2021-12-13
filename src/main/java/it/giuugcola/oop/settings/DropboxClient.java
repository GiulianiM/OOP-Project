package it.giuugcola.oop.settings;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import it.giuugcola.oop.exceptions.DropboxExceptions;

public class DropboxClient {

    private final String clientIdentifier = "dropbox/Progetto-OOP";
    private final String APP_TOKEN = "RrwYr735p88AAAAAAAAAAfVC-Wr-r0bG-JydWI9l5xJY2onu0wcJStqFrD895QWv";
    public static DbxClientV2 client;

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
