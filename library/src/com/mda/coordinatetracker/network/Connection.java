package com.mda.coordinatetracker.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: mda
 * Date: 4/1/13
 * Time: 11:35 AM
 */
public class Connection {

    public static HttpURLConnection createConnection(String url) throws IOException {
        URL serverAddress = new URL(url);
        //Set up the initial connection
        HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setReadTimeout(10000);

        return  connection;
    }


}
