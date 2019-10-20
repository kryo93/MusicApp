package com.musix.DAO;

import com.musix.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfflinePlaylistRepository extends JpaRepository<Playlist, Integer> {

}
