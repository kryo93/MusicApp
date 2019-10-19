package com.musix.service;

import com.musix.DAO.TrackRepository;
import com.musix.entity.Track;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TrackService {

    private TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getTopTracks() throws IOException, JSONException {
        return trackRepository.getTopTracks();
    }

//    public List<Track> searchTrack(String searchQuery) {
//    }
}
