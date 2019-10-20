package com.musix.service;

import com.musix.DAO.OfflinePlaylistRepository;
import com.musix.DAO.OfflineTrackRespository;
import com.musix.entity.Playlist;
import com.musix.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfflineTrackService {

    private OfflineTrackRespository offlineTrackRespository;
    // a service should use repositories instead of other services, separation of concerns,
    // which is pretty crap at this moment
    private OfflinePlaylistRepository offlinePlaylistRepository;

    @Autowired
    public OfflineTrackService(OfflineTrackRespository offlineTrackRespository, OfflinePlaylistRepository offlinePlaylistRepository) {
        this.offlineTrackRespository = offlineTrackRespository;
        this.offlinePlaylistRepository = offlinePlaylistRepository;
    }

    public Track saveTrack(Track track) {
        offlineTrackRespository.save(track);
        return track;
    }

    public Optional<Track> deleteTrackOfPlaylist(int track_id, Integer list_id) {
        Optional<Playlist> playlist = offlinePlaylistRepository.findById(list_id);
        Optional<Track> track = offlineTrackRespository.findById(track_id);
        if (playlist.isPresent()){
            String playlist_name = playlist.get().getPlaylistName();
            offlineTrackRespository.deleteTrackOfPlaylist(playlist_name, track_id);
        }
        return track;
    }

//    public List<String> getPlaylists() {
//        return offlineRespository.getDistinctPlaylist();
//    }

    public List<Track> getAllTracks() {
        return offlineTrackRespository.findAll();
    }

    public List<Track> getAllTracksFromPlaylist(Integer list_id) {
        Optional<Playlist> playlist = offlinePlaylistRepository.findById(list_id);
        if(playlist.isPresent()) {
            String playlist_name = playlist.get().getPlaylistName();
            return offlineTrackRespository.getAllTrackFromPlaylist(playlist_name);
        }

        return null;
    }

    public void deleteAllTrackofPlaylist(Integer list_id) {
        Optional<Playlist> playlist = offlinePlaylistRepository.findById(list_id);
        if (playlist.isPresent()){
            String playlist_name = playlist.get().getPlaylistName();
            offlineTrackRespository.deleteAllTrackFromPlaylist(playlist_name);
        }
    }
}
