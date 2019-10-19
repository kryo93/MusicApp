package com.musix.controller;

import com.musix.entity.Artist;
import com.musix.entity.Track;
import com.musix.service.AlbumService;
import com.musix.service.ArtistService;
import com.musix.service.OfflineService;
import com.musix.service.TrackService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;


@Controller
public class MusicController {

    private AlbumService albumService;
    private TrackService trackService;
    private ArtistService artistService;
    private OfflineService offlineService;
//    AlbumService albumService;

    @Autowired
    public MusicController(TrackService trackService, AlbumService albumService, ArtistService artistService, OfflineService offlineService) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.offlineService = offlineService;
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
        if(artistName == null) {
            model.addAttribute("top_tracks", trackService.getTopTracks());
            model.addAttribute("artist", null);
        } else {
            System.out.println(artistName);
            model.addAttribute("top_tracks", artistService.getTopTracks(artistName));
            model.addAttribute("artist", artistName);
        }
        return "tracks";
    }

    @GetMapping("/saveTrack")
    public String saveTrack(@RequestParam("track") String trackName, @RequestParam("artist") String artistName){
        Track track = new Track(trackName, artistName);
        offlineService.saveTrack(track);
        return "redirect:/";
    }

    @GetMapping("/playlist")
    public String playlistContent(@RequestParam(value = "list", required = false) String playlist, Model model){
        if (playlist != null) {
            List<Track> tracks = offlineService.getAllTracks();
            model.addAttribute("tracks", tracks);
            model.addAttribute("list", playlist);
            return "playlistcontent";
        } else {
            List<String> playlists = offlineService.getPlaylists();
            model.addAttribute("playlists", playlists);
            return "playlist";
        }
    }

    @GetMapping("/playlist/delete")
    public String deleteTrack(@RequestParam("list") String playlist, @RequestParam("id") int trackId){
        offlineService.deleteTrack(trackId);
        return "redirect:/playlist?list=" + playlist;
    }


}
