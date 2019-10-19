package com.musix.entity;

public class Track {
    private String trackName;
    private String artistName;
    private Long playCount;
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

    @Override
    public String toString() {
        return "Track{" +
                "trackName='" + trackName + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
