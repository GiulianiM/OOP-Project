package it.giuugcola.OOPProject.settings;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class DropboxClient {

    private static final String APP_TOKEN = "RrwYr735p88AAAAAAAAAAfVC-Wr-r0bG-JydWI9l5xJY2onu0wcJStqFrD895QWv";

    public static DbxClientV2 getClient() {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Progetto-OOP").build();
        return new DbxClientV2(config, APP_TOKEN);
    }
}
