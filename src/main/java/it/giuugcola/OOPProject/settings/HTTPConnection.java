package it.giuugcola.OOPProject.settings;

import java.net.HttpURLConnection;

public interface HTTPConnection {
    HttpURLConnection getConnection(String url);
}
