package com.musix.DAO;

import com.musix.entity.Artist;
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

@Repository
public class ArtistRepository {

    private final String base_url = "http://ws.audioscrobbler.com/2.0/";

    @Value("${lastfm.api_key}")
    private String api_key;

    private String format = "json";

    public HttpURLConnection getConnection(String method, String requestType) throws IOException {
        String urlString = String.format("%s?method=%s&api_key=%s&format=%s", base_url, method, api_key, format);
        System.out.println(urlString);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestType);
        return connection;
    }

    public String getJSONString(HttpURLConnection connection) throws IOException {
        StringBuffer response = new StringBuffer();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String readLine;
            while ((readLine = input.readLine()) != null) {
                response.append(readLine);
            }
            input.close();
        }

        return String.valueOf(response);
    }


    public List<Artist> getTopArtists() throws IOException, JSONException {
        String method = "chart.gettopartists";
        HttpURLConnection connection = getConnection(method, "GET");
        String response = getJSONString(connection);

        JSONObject result = new JSONObject(response);
        JSONObject tracks = result.getJSONObject("artists");
        JSONArray trackArray = tracks.getJSONArray("artist");

        List<Artist> artistList = new ArrayList<>();

        for (int i = 0; i < trackArray.length(); i++) {
            JSONObject trackObject = trackArray.getJSONObject(i);
            String artistName = trackObject.getString("name");
            artistList.add(new Artist(artistName));
        }
        return artistList;
    }

    public List<Artist> searchResult(String searchQuery, int pageNumber, int limit) throws IOException, JSONException {
        String method = "artist.search&artist=" + searchQuery + "&page=" + pageNumber + "&limit=" + limit;
        HttpURLConnection connection = getConnection(method, "GET");
        String response = getJSONString(connection);

        JSONObject result = new JSONObject(response);
        JSONObject search_results = result.getJSONObject("results");
        String totalResults = search_results.getString("opensearch:totalResults");
        System.out.println(totalResults);
        List<Artist> artistList = new ArrayList<>();

        if (Integer.parseInt(totalResults) == 0) {
            return artistList;
        } else {
            JSONObject artistMatches = search_results.getJSONObject("artistmatches");
            JSONArray artistArray = artistMatches.getJSONArray("artist");
            for (int i = 0; i < artistArray.length(); i++) {
                JSONObject eachArtist = artistArray.getJSONObject(i);
                String artistName = eachArtist.getString("name");
                Long listeners = Long.parseLong(eachArtist.getString("listeners"));
                artistList.add(new Artist(artistName, listeners));
            }
            return artistList;
        }
    }

    public List<Track> getTopTracks(String artistName) throws Exception {
        List<Track> tracks = new ArrayList<>();
        String method = "artist.gettoptracks&artist=" + artistName;
        HttpURLConnection connection = getConnection(method, "GET");
        String response = getJSONString(connection);
        if (response == null || response.length() == 0){
            return tracks;
        }

        JSONObject result = new JSONObject(response);
        try{
            String error = result.getString("error");
            System.out.println(error);
            return tracks;
        } catch (Exception e){
            System.out.println("Found them tracks");
        }

        JSONObject toptracks = result.getJSONObject("toptracks");
        JSONArray trackarray = toptracks.getJSONArray("track");

        for(int i = 0; i < trackarray.length(); i++){
            JSONObject eachtrack = trackarray.getJSONObject(i);
            String trackName = eachtrack.getString("name");
            Long listeners = Long.parseLong(eachtrack.getString("listeners"));
            Long playcount = Long.parseLong(eachtrack.getString("playcount"));
            tracks.add(new Track(trackName, artistName, playcount, listeners));
        }

        return tracks;
    }
}

//while searching, search both the internal database and the external database
