package com.musix.entity;

public class Artist {
    private String artistName;
    private Long listeners;

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(String artistName, Long listeners) {
        this.artistName = artistName;
        this.listeners = listeners;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Long getListeners() {
        return listeners;
    }

    public void setListeners(Long listeners) {
        this.listeners = listeners;
    }
}
