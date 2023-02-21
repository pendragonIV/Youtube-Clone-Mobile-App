package com.example.myapplication.Obj;

import java.io.Serializable;

public class VideoIn4 implements Serializable {
    private String title;
    private String thumbnail;
    private String owner;
    private String videoId;
    private String description;

    public VideoIn4(String title, String thumbnail, String owner, String videoId, String description) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.owner = owner;
        this.videoId = videoId;
        this.description = description;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
