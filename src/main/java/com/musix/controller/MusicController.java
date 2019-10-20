package com.musix.controller;

import com.musix.entity.Artist;
import com.musix.entity.Playlist;
import com.musix.entity.Track;
import com.musix.service.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;


@Controller
public class MusicController {

    private AlbumService albumService;
    private TrackService trackService;
    private ArtistService artistService;
    private OfflineTrackService offlineTrackService;
    private OfflinePlaylistService offlinePlaylistService;

    @Autowired
    public MusicController(TrackService trackService, AlbumService albumService, ArtistService artistService, OfflineTrackService offlineTrackService, OfflinePlaylistService offlinePlaylistService) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.offlineTrackService = offlineTrackService;
        this.offlinePlaylistService = offlinePlaylistService;
    }

    /*I don't even need this controller, and spring
boot will by default will render the "index" page*/
    @GetMapping({"/", ""})
    public String showHomepage() {
        System.out.println("Inside home controller");
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search_query") String searchQuery, @RequestParam("page_number") int pageNumber, Model model) throws IOException, JSONException {
        List<Artist> resultArtist = artistService.searchArtist(searchQuery, pageNumber, 10);
        model.addAttribute("search_query", searchQuery);
        model.addAttribute("current_page", pageNumber);
        model.addAttribute("artist_search_result", resultArtist);

        return "artist";
    }

    @GetMapping("/topartists")
    public String showTopTracksForArtist(Model model) throws IOException, JSONException {
        model.addAttribute("search_query", "all");
        model.addAttribute("top_artist", artistService.getTopArtists());
        return "artist";
    }

    @GetMapping("/toptracks")
    public String showTopTracks(@RequestParam(value = "artist", required = false) String artistName, Model model) throws Exception {
        model.addAttribute("playlist", new Playlist());
        model.addAttribute("playlists", offlinePlaylistService.getAllPlaylist());
        model.addAttribute("artist", artistName);
        if (artistName == null) {
            model.addAttribute("top_tracks", trackService.getTopTracks());
        } else {
            System.out.println(artistName);
            model.addAttribute("top_tracks", artistService.getTopTracks(artistName));
        }
        return "tracks";
    }

    @GetMapping("/saveTrack")
    public String saveTrack(@RequestParam("track") String trackName, @RequestParam("artist") String artistName, @RequestParam(value = "list") String playlist) {
        Playlist playlist1 = new Playlist(playlist);
        Track track = new Track(trackName, artistName, playlist);
        offlineTrackService.saveTrack(track);
        return "redirect:/";
    }

    @GetMapping("/playlist")
    public String playlistContent(@RequestParam(value = "list", required = false) String playlist, Model model){
        if (playlist != null) {
            System.out.println("When params");
            List<Track> tracks = offlineTrackService.getAllTracksFromPlaylist(playlist);
            model.addAttribute("tracks", tracks);
            model.addAttribute("list", playlist);
            model.addAttribute("update_playlist", offlinePlaylistService.getPlaylistByName(playlist));            return "playlistcontent";
        } else {
            System.out.println("When not params");
            List<Playlist> playlists = offlinePlaylistService.getAllPlaylist();
            model.addAttribute("playlists", playlists);
            model.addAttribute("add_playlist", new Playlist());
            return "playlist";
        }
    }

    @PostMapping("/playlist/add")
    public String addPlaylist(@ModelAttribute("new_playlist") Playlist playlist){
        offlinePlaylistService.savePlaylist(playlist);
        return "redirect:/playlist";
    }

    @GetMapping("/playlist/deleteplaylist")
    public String deletePlaylist(@RequestParam("list") String playlistName){
        offlinePlaylistService.deletePlaylist(playlistName);
        offlineTrackService.deleteTrackofPlaylist(playlistName);
        /*If only I had many to many relationship between playlist and tracks, then the second call
        * wouldn't have to be made. And I wouldn't have to populate my database with duplicate tracks
        * in which belongs to different playlist. If Only! Goddamn it! I must do it!!!!!!*/
        return "redirect:/playlist";
    }

    // can I send parameters from one function to another ?
    @PostMapping("/playlist/updateplaylist")
    public String updatePlaylist(@ModelAttribute("playlist") Playlist playlist){
        offlinePlaylistService.savePlaylist(playlist);
        return "redirect:/playlist?list=" ;
    }

    @GetMapping("/playlist/delete")
    public String deleteTrack(@RequestParam("list") String playlist, @RequestParam("id") int trackId){
        offlineTrackService.deleteTrack(trackId);
        return "redirect:/playlist?list=" + playlist;
    }


}



// LESSON LEARNED, NEVER PLAY AROUND WITH ENTITES THAT YOU MAKE VISIBLE TO THE END USER FOR CRUD OPERATIONS! IN THE BACKED;
// need to refactor using id from playlist to ease navigation when updating the name of the playlist