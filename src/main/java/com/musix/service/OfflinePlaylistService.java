package com.musix.service;

import com.musix.DAO.OfflinePlaylistRepository;
import com.musix.entity.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfflinePlaylistService {

    private OfflinePlaylistRepository offlinePlaylistRepository;

    @Autowired
    public OfflinePlaylistService(OfflinePlaylistRepository offlinePlaylistRepository) {
        this.offlinePlaylistRepository = offlinePlaylistRepository;
    }
    public Playlist addPlaylist(Playlist playlist) {
        offlinePlaylistRepository.save(playlist);
        return playlist;
    }

    public Optional<Playlist> deletePlaylist(String playlist_name) {
        Optional<Playlist> playlist = offlinePlaylistRepository.findOfName(playlist_name);
        offlinePlaylistRepository.deleteOfName(playlist_name);
        return playlist;
    }

    public List<Playlist> getAllPlaylist() {
        return offlinePlaylistRepository.findAll();
    }

//    public List<Track> getAllTracks(int playlist_id){
//        return offlinePlaylistRepository.findTracksofPlaylistbyId(playlist_id);
//    }
}
