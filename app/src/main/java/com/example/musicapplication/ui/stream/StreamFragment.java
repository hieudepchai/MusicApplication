package com.example.musicapplication.ui.stream;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;

public class StreamFragment extends Fragment {
    private MediaPlayer mp;
    private MainActivity mainActivity = (MainActivity) getActivity();
    private boolean playPause = true;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stream, container, false);

        mp = new MediaPlayer();
        String streamURL = "http://192.168.0.105:2000";

        //setting the audio stream type to Streaming music
        try{

            mp.setAudioStreamType( AudioManager.STREAM_MUSIC);
            mp.setDataSource(streamURL);
            mp.prepareAsync();

        }catch (Exception e){
            Log.e("stream:", "error");
            e.printStackTrace();
        }

        //catching the error if any
        mp.setOnErrorListener( new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                mediaPlayer.reset();
                return false;
            }
        } );
        Button playButton = root.findViewById( R.id.publish );
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String uri = "192.168.0.105:8000/playback.mp3";
                if(playPause){
                    Log.e("Stream", "start=============");
                    mp.start();
                    playPause=false;
                }else{
                    mp.pause();
                    playPause=true;
                }
                mp.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {


                    }
                } );
            }
        } );

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        //mainActivity.hideMainPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
    }

}