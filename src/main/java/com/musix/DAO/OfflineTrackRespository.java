package com.musix.DAO;

import com.musix.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineTrackRespository extends JpaRepository<Track, Integer> {
    @Query("select tr from Track tr where tr.playlist=:playlist")
    List<Track> getAllTrackFromPlaylist(@Param("playlist") String playlist);

//    @Query("SELECT DISTINCT track.playlist FROM Track track")
//    List<String> getDistinctPlaylist();
}
