package com.example.musicapplication.ui.playing;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;

import java.security.Key;

import co.mobiwise.library.OnActionClickedListener;


public class SongPlayingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int pauseCurrentPosition;
    MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    private boolean playPause;
    private ProgressDialog progressDialog;
    private ItemTrackAdapter trackAdapter;

    private OnFragmentInteractionListener mListener;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    public SongPlayingFragment() {
        // Required empty public constructor
    }

    public static SongPlayingFragment newInstance() {
        SongPlayingFragment fragment = new SongPlayingFragment();
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

//init mediaplayer
        mediaPlayer = new MediaPlayer();
        progressDialog = new ProgressDialog(getActivity());

        mediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC);

        //recycler View
        trackAdapter = new ItemTrackAdapter(this, mediaPlayer, progressDialog);
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

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
