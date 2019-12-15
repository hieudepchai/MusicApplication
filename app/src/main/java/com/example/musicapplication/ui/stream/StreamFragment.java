package com.example.musicapplication.ui.stream;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;

import java.util.concurrent.TimeUnit;

import dyanamitechetan.vusikview.VusikView;

public class StreamFragment extends Fragment implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mp;
    private boolean playPause = true;

    private ImageButton btn_play_pause;
    private SeekBar seekBar;
    private TextView textView;

    private VusikView musicView;

    private int mediaFileLenght;
    final Handler handler = new Handler(  );

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stream, container, false);

        musicView = root.findViewById( R.id.musicView );

        btn_play_pause = root.findViewById( R.id.btn_play_pause );
        seekBar = root.findViewById( R.id.seekbar );
        seekBar.setMax( 99 ); //100% (0-99)
        seekBar.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mp.isPlaying()){
                    SeekBar seekBar = (SeekBar) v;
                    int playPosition = (mediaFileLenght/100)*seekBar.getProgress();
                    mp.seekTo( playPosition );
                }
                return false;
            }
        } );

        textView = root.findViewById( R.id.textTimer );

        mp = new MediaPlayer();
        mp.setOnBufferingUpdateListener( this );
        mp.setOnCompletionListener( this );
        String streamURL = "http://192.168.0.105:2000/";

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


        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mediaFileLenght = mp.getDuration();
                musicView.start();

                if(!mp.isPlaying()){ //playPause
                    Log.e("Stream", "start=============");
                    mp.start();

                    musicView.resumeNotesFall();
                    btn_play_pause.setImageResource( R.drawable.ic_pause_black_24dp );
                    playPause=false;
                }else{
                    mp.pause();
                    musicView.pauseNotesFall();
                    btn_play_pause.setImageResource( R.drawable.ic_play_arrow_black_24dp );
                    playPause=true;
                }
                updateSeekBar();

            }

            private void updateSeekBar() {
                seekBar.setProgress( (int) (((float)mp.getCurrentPosition()/mediaFileLenght)*100));
                if(mp.isPlaying()){
                    Runnable updater = new Runnable() {
                        @Override
                        public void run() {
                            updateSeekBar();
                            textView.setText( String.format( "%d:%d", ((mp.getCurrentPosition() % (1000*60*60)) / (1000*60)),
                                    ((mp.getCurrentPosition() % (1000*60*60)) / 1000)) );
                        }
                    };
                    handler.postDelayed( updater, 1000 ); //1 seconds
                }
            }
        } );

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity.hideMainPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.showMainPlayer();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;

        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress( percent );
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btn_play_pause.setImageResource( R.drawable.ic_play_arrow_black_24dp );
        musicView.stopNotesFall();
    }
}