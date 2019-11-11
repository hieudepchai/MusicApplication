package com.example.musicapplication.service;

public class RetrofitService {
    private static String base_url = "http://musicplayer.eihnim.com/api/";
    public static RetrofitInterface getService(){
        return RetrofitClient.getClient(base_url).create(RetrofitInterface.class);
    }
}
