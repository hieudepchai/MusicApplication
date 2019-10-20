package com.example.musicapplication.model;

public class Song {
    Long id;
    String name;
    String singer;
    String composer;
    String album;
    String lyric;
    String source;
    String genre1;
    String genre2;
    String genre3;
    String genre4;
    String genre5;
    String weburl;
    String downloadurl;
    String thumbnail;
    public Song(){

    }
    public Song(Long id, String name, String singer, String composer, String album, String lyric, String source, String genre1, String genre2, String genre3, String genre4, String genre5, String weburl, String downloadurl, String thumbnail) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.composer = composer;
        this.album = album;
        this.lyric = lyric;
        this.source = source;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.genre3 = genre3;
        this.genre4 = genre4;
        this.genre5 = genre5;
        this.weburl = weburl;
        this.downloadurl = downloadurl;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getGenre3() {
        return genre3;
    }

    public void setGenre3(String genre3) {
        this.genre3 = genre3;
    }

    public String getGenre4() {
        return genre4;
    }

    public void setGenre4(String genre4) {
        this.genre4 = genre4;
    }

    public String getGenre5() {
        return genre5;
    }

    public void setGenre5(String genre5) {
        this.genre5 = genre5;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
