package it.giuugcola.OOPProject.settings;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class DropboxClient implements Constants{

    public static DbxClientV2 getClient() {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Progetto-OOP").build();
        DbxClientV2 client = new DbxClientV2(config, APP_TOKEN);

        return client;
    }
}
