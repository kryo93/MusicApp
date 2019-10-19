package com.musix.DAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class AlbumRepository {
    private final String base_url = "http://ws.audioscrobbler.com/2.0/";

    @Value("${lastfm.api_key}")
    private String api_key;

    private String format = "json";

    public HttpURLConnection getConnection(String method, String requestType) throws IOException {
        String urlString = String.format("%s?method=%s&api_key=%s&format=%s", base_url, method, api_key, format);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestType);
        return connection;
    }

    public String getJSONString(HttpURLConnection connection) throws IOException {
        StringBuffer response = new StringBuffer();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String readLine = null;
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((readLine = input.readLine()) != null) {
                response.append(readLine);
            }
            input.close();
        }

        return String.valueOf(response);
    }

}
