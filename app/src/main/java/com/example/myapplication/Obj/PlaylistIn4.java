package com.example.myapplication.Obj;

import java.io.Serializable;

public class PlaylistIn4 implements Serializable {
    private String title;
    private String thumbnail;
    private String playlistId;

    public PlaylistIn4(String title, String thumbnail, String playlistId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.playlistId = playlistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
