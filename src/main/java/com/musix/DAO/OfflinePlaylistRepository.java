package com.musix.DAO;

import com.musix.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OfflinePlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("select pl from Playlist pl where pl.playlistName=:playlist_name")
    Optional<Playlist> findOfName(@Param("playlist_name") String playlist_name);

    @Transactional
    @Modifying
    @Query("delete from Playlist pl where pl.playlistName=:playlist_name")
    void deleteOfName(@Param("playlist_name") String playlist_name);
}
