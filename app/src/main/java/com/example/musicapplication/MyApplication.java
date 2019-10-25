package com.example.musicapplication;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.musicapplication.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private MyApplication MyAppInstance;
    private FirebaseDatabase fbDatabase;
    private DatabaseReference dbRefSong;
    private List<Song> listSong = new ArrayList<>();
    private final String RefSongTag = "dbRefSong";
    public interface MyAppCallback{
        public void SongCallBack(List<Song> listSong);
    }
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        MyAppInstance = this;
        initFirebase();
    }
    //
    public void initFirebase(){
        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbRefSong = fbDatabase.getReference("songs");
    }
    public void addRealtimeUpdate(final MyAppCallback callBack){
        dbRefSong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSong.clear();
                Log.e(RefSongTag, "No of Songs: " + dataSnapshot.getChildrenCount());
                for(DataSnapshot songSnapshot: dataSnapshot.getChildren()){
                    Song song = songSnapshot.getValue(Song.class);
                    Log.e(RefSongTag, song.getName());
                    listSong.add(song);
                }
                callBack.SongCallBack(listSong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(RefSongTag, "Failed to read value.", databaseError.toException());
            }
        });
    }
    //
    public List<Song> getListSong(){
        return listSong;
    }
    public void setListSong(List<Song> listSong){
        this.listSong = listSong;
    }
    //

}