package com.musix.service;

import com.musix.DAO.ArtistRepository;
import com.musix.entity.Artist;
import com.musix.entity.Track;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ArtistService {

    private ArtistRepository artistRepository;


    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getTopArtists() throws IOException, JSONException {
        return  artistRepository.getTopArtists();
    }

    public List<Artist> searchArtist(String searchQuery, int pageNumber, int pageLimit) throws IOException, JSONException {
        return artistRepository.searchResult(searchQuery, pageNumber, pageLimit);
    }

    public List<Track> getTopTracks(String artistName) throws Exception {
        return artistRepository.getTopTracks(artistName);
    }
}
