package com.musix.service;

import com.musix.DAO.OfflineRespository;
import com.musix.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfflineService {

    private OfflineRespository offlineRespository;

    @Autowired
    public OfflineService(OfflineRespository offlineRespository) {
        this.offlineRespository = offlineRespository;
    }

    public Track saveTrack(Track track) {
        offlineRespository.save(track);
        return track;
    }

    public Optional<Track> deleteTrack(int id) {
        Optional<Track> track = offlineRespository.findById(id);
        offlineRespository.deleteById(id);
        return track;
    }

    public List<String> getPlaylists() {
        return offlineRespository.getDistinctPlaylist();
    }

    public List<Track> getAllTracks() {
        return offlineRespository.findAll();
    }
}
