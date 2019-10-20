package com.example.musicapplication.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRefSong = fbDatabase.getReference("songs");
    private List<Song> listSong = new ArrayList<>();
    private final String RefSongTag = "dbRefSong";
    public DatabaseManager(){
        readSongs();
    }
    public void readSongs(){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(RefSongTag, "Failed to read value.", databaseError.toException());
            }
        });
    }
    public List<Song> getListSong(){
        return listSong;
    }
}
