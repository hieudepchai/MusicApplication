package com.example.musicapplication;

import android.app.Application;
import android.os.AsyncTask;

import com.example.musicapplication.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private FirebaseDatabase fbDatabase;
    private DatabaseReference dbRefSong;
    private List<Song> listSong = new ArrayList<>();
    private final String RefSongTag = "dbRefSong";
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        AsyncTask<Void, Void, String> s = new GetDataFirebaseAPITask().execute();
    }
    public void initFirebase(){
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbRefSong = fbDatabase.getReference("songs");
    }
    public void getListSongViaAPI() throws IOException {
        URL url = new URL("https://mymusic-0000-255417.firebaseio.com/songs.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
    }
}