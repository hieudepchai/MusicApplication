package com.example.musicapplication.model;

import java.util.List;

public class CategoryInfo {
    private String title;
    private String description;
    private List<Song> mPieceSong;

    public CategoryInfo(String title, String description, List<Song> mPieceSong){
        this.title = title;
        this.description = description;
        this.mPieceSong = mPieceSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getmPieceSong() {
        return mPieceSong;
    }

    public void setmPieceSong(List<Song> mPieceSong) {
        this.mPieceSong = mPieceSong;
    }
}
