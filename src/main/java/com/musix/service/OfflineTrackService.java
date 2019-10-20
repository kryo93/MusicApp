package com.musix.service;

import com.musix.DAO.OfflineTrackRespository;
import com.musix.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfflineTrackService {

    private OfflineTrackRespository offlineTrackRespository;

    @Autowired
    public OfflineTrackService(OfflineTrackRespository offlineTrackRespository) {
        this.offlineTrackRespository = offlineTrackRespository;
    }

    public Track saveTrack(Track track) {
        offlineTrackRespository.save(track);
        return track;
    }

    public Optional<Track> deleteTrack(int id) {
        Optional<Track> track = offlineTrackRespository.findById(id);
        offlineTrackRespository.deleteById(id);
        return track;
    }

//    public List<String> getPlaylists() {
//        return offlineRespository.getDistinctPlaylist();
//    }

    public List<Track> getAllTracks() {
        return offlineTrackRespository.findAll();
    }

    public List<Track> getAllTracksFromPlaylist(String playlist) {
        return offlineTrackRespository.getAllTrackFromPlaylist(playlist);
    }
}
