package com.example.musicapplication.ui.playing;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.musicapplication.R;

import java.io.IOException;

import butterknife.BindView;
import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SongPlayingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SongPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongPlayingFragment extends Fragment implements OnActionClickedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int pauseCurrentPosition;
    MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    private boolean playPause;
    private ProgressDialog progressDialog;
    private ItemTrackAdapter trackAdapter;

    private OnFragmentInteractionListener mListener;

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
//create the time progress for progressbar
//        final InteractivePlayerView mInteractivePlayerView = root1.findViewById(R.id.interactivePlayerView);
//        mInteractivePlayerView.setMax(60);
//        mInteractivePlayerView.setProgress(0);
//        mInteractivePlayerView.setOnActionClickedListener(this);
//
//        final String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3"; // your URL here
        mediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC);

        //recycler View
        trackAdapter = new ItemTrackAdapter(getActivity(), mediaPlayer, progressDialog);
        RecyclerView musicRecyclerView = root.findViewById( R.id.musicRecyclerView );
        musicRecyclerView.setAdapter( trackAdapter );
        musicRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

//set the button when click
//        final ImageView imageView = (ImageView) root1.findViewById(R.id.control);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!playPause) {
//                    imageView.setBackgroundResource(R.drawable.ic_action_pause);
//                    mInteractivePlayerView.start();
//
//                    if (initialStage) {
//                        new Player().execute(url);
//
//                    } else {
//                        if (!mediaPlayer.isPlaying()){
//                            mInteractivePlayerView.start();
//                        mediaPlayer.seekTo(pauseCurrentPosition);
//                        mediaPlayer.start();}
//
//                        if(mediaPlayer==null) {
//                            mInteractivePlayerView.start();
//                            mediaPlayer.start();
//                        }
//                        playPause = true;
//}
//                } else {
//                    imageView.setBackgroundResource(R.drawable.ic_action_play);
//
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                        mInteractivePlayerView.stop();
//                        pauseCurrentPosition=mediaPlayer.getCurrentPosition();
//                    }
//
//                    playPause = false;
//                }
//            }
//        });

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

//    class Player extends AsyncTask<String, Void, Boolean> {
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            Boolean prepared = false;
//
//            try {
//                mediaPlayer.setDataSource(strings[0]);
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        initialStage = true;
//                        playPause = false;
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                    }
//                });
//
//                mediaPlayer.prepare();
//                prepared = true;
//
//            } catch (Exception e) {
//                Log.e("MyAudioStreamingApp", e.getMessage());
//                prepared = false;
//            }
//
//            return prepared;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//
//            if (progressDialog.isShowing()) {
//                progressDialog.cancel();
//
//            }
//
//            mediaPlayer.start();
//            initialStage = false;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
//        }
//    }

    @Override
    public void onActionClicked(int id) {
        switch (id) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
