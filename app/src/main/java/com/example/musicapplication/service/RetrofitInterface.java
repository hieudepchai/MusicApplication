package com.example.musicapplication.service;

import com.example.musicapplication.model.Genre;
import com.example.musicapplication.model.Singer;
import com.example.musicapplication.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface  RetrofitInterface {
    @GET("song")
    Call<List<Song>> getSong();
    @GET("singer")
    Call<List<Singer>> getSinger();
    @GET("genre")
    Call<List<Genre>> getGenre();

}
