package com.example.musicapplication;

import android.app.Application;
import android.util.Log;

import com.example.musicapplication.model.Song;
import com.example.musicapplication.service.RetrofitInterface;
import com.example.musicapplication.service.RetrofitService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplication extends Application {
    private List<Song> listSong;

    @Override
    public void onCreate() {
        super.onCreate();

    }
    public void loadSong() throws IOException {

    }
    public void setListSong(List<Song> inputListSong){
        listSong = inputListSong;
    }
    public List<Song> getListSong(){
        return listSong;
    }
}