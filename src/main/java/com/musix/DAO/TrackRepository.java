package com.musix.DAO;

import com.musix.entity.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

///2.0/?method=artist.gettoptracks&artist=cher&api_key=YOUR_API_KEY&format=json
@Repository
public class TrackRepository {
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

    public List<Track> getTopTracks() throws IOException, JSONException {
        String method = "chart.gettoptracks";
        HttpURLConnection connection = getConnection(method, "GET");
        String response = getJSONString(connection);

        JSONObject result = new JSONObject(response);
        JSONObject tracks = result.getJSONObject("tracks");
        JSONArray trackArray = tracks.getJSONArray("track");

        List<Track> trackList = new ArrayList<>();

        for (int i = 0; i < trackArray.length(); i++) {
            JSONObject trackObject = trackArray.getJSONObject(i);
            String trackName = trackObject.getString("name");
            JSONObject artist = trackObject.getJSONObject("artist");
            String artistName = artist.getString("name");
            trackList.add(new Track(trackName, artistName));
        }
        return trackList;
    }

    public List<Track> searchResult(String searchQuery, int pageNumber, int pageLimit) throws IOException, JSONException {
        String method = "track.search&track=" + searchQuery + "&page=" + pageNumber + "&limit=" + pageLimit;
        HttpURLConnection connection = getConnection(method, "GET");
        String response = getJSONString(connection);

        JSONObject result = new JSONObject(response);
        JSONObject search_results = result.getJSONObject("results");
        String totalResults = search_results.getString("opensearch:totalResults");
        List<Track> trackList = new ArrayList<>();

        if (Integer.parseInt(totalResults) == 0) {
            return trackList;
        } else {
            JSONObject trackMatches = search_results.getJSONObject("trackmatches");
            JSONArray trackArray = trackMatches.getJSONArray("track");
            for (int i = 0; i < trackArray.length(); i++) {
                JSONObject eachTrack = trackArray.getJSONObject(i);
                String trackName = eachTrack.getString("name");
                String artistName = eachTrack.getString("artist");
                Long listeners = Long.parseLong(eachTrack.getString("listeners"));
                trackList.add(new Track(trackName, artistName, listeners));
            }
            return trackList;
        }



    }
}