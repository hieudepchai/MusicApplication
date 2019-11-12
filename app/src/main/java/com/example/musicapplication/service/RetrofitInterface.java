package com.example.musicapplication.service;

import com.example.musicapplication.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface  RetrofitInterface {
    @GET("song")
    Call<List<Song>> getSong();
}
