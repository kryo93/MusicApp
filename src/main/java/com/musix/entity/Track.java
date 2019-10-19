package com.musix.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private int id;

    @Column(name = "track_name")
    private String trackName;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "playlist")
    @ColumnDefault("'default'")
    private String playlist = "default";

    @Transient
    private Long playCount;

    @Transient
    private Long listeners;

    public Track() {
    }

    public Track(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public Track(String trackName, String artistName, Long playCount, Long listeners) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.playCount = playCount;
        this.listeners = listeners;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getListeners() {
        return listeners;
    }

    public void setListeners(Long listeners) {
        this.listeners = listeners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackName='" + trackName + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
