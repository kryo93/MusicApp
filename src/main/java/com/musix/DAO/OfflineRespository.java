package com.musix.DAO;

import com.musix.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineRespository extends JpaRepository<Track, Integer> {
    @Query("SELECT DISTINCT track.playlist FROM Track track")
    List<String> getDistinctPlaylist();
}
