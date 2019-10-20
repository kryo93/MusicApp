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
    public Playlist savePlaylist(Playlist playlist) {
        offlinePlaylistRepository.save(playlist);
        return playlist;
    }

    public Playlist deletePlaylist(Integer list_id) {
        Optional<Playlist> playlist = offlinePlaylistRepository.findById(list_id);
        offlinePlaylistRepository.deleteById(list_id);
        return playlist.get();
    }

    public List<Playlist> getAllPlaylist() {
        return offlinePlaylistRepository.findAll();
    }

    public Object getPlaylistByName(String playlist) {
        return offlinePlaylistRepository.findOfName(playlist);
    }

    public Optional<Playlist> getPlaylistById(Integer list_id) {
        System.out.println("I'm looking for playlist by id");
        return offlinePlaylistRepository.findById(list_id);
    }

//    public List<Track> getAllTracks(int playlist_id){
//        return offlinePlaylistRepository.findTracksofPlaylistbyId(playlist_id);
//    }
}
