package com.example.musicapplication.ui.playing;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;

import java.util.List;


public class SongPlayingFragment extends Fragment {

    MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private ItemTrackAdapter trackAdapter;
    private Song playingSong;
    private List<Song> latestSong;

    private MainActivity mainActivity = (MainActivity) getActivity();

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    public SongPlayingFragment(Song playingSong, List<Song> latestSong) {
        // Required empty public constructor
        this.playingSong = playingSong;
        this.latestSong = latestSong;
    }

    public static SongPlayingFragment newInstance(Song playingSong, List<Song> latestSong) {
        SongPlayingFragment fragment = new SongPlayingFragment(playingSong, latestSong);
        Bundle args = new Bundle();

        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate( R.layout.fragment_song_playing, container, false );
        Log.e("song playing", "onCreateView");
        //init mediaplayer
        mediaPlayer = mainActivity.getMediaPlayer();
        progressDialog = new ProgressDialog(getActivity());

        //recycler View
        trackAdapter = new ItemTrackAdapter(getActivity(), this, mediaPlayer, progressDialog, playingSong, latestSong);
        RecyclerView musicRecyclerView = root.findViewById( R.id.musicRecyclerView );
        musicRecyclerView.setAdapter( trackAdapter );
        musicRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
//
        root.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                onBackPressed();
                Log.d("Test swipe","Swipe left");
            }
            @Override
            public void onSwipeRight() {
                onBackPressed();
                Log.d("Test swipe","Swipe right");

            }

            });

        return root;
    }

    public void onPause() {
        super.onPause();
        Log.e("song playing", "onpause");

//        if (mediaPlayer != null) {
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("song playing", "onDestroy");
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top )
                .hide(this)
                .commit();
        MainActivity.showMainPlayer();
    }

}
