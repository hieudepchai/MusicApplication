package com.example.musicapplication.ui.playing;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.musicapplication.R;

import java.io.IOException;

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
    MediaPlayer mediaPlayer = new MediaPlayer();

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

        ItemTrackAdapter trackAdapter = new ItemTrackAdapter(getActivity());
        RecyclerView trackItem = root.findViewById( R.id.track_view );
        trackItem.setAdapter( trackAdapter );
        trackItem.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        final InteractivePlayerView mInteractivePlayerView = root.findViewById(R.id.interactivePlayerView);
        mInteractivePlayerView.setMax(60);
        mInteractivePlayerView.setProgress(0);
        mInteractivePlayerView.setOnActionClickedListener(this);
        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3"; // your URL here
        mediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ImageView imageView = (ImageView) root.findViewById(R.id.control);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mInteractivePlayerView.isPlaying()) {
                    if(mediaPlayer==null) {
                        mInteractivePlayerView.start();
                        mediaPlayer.start();
                        imageView.setBackgroundResource(R.drawable.ic_action_pause);
                    }
                    if(!mediaPlayer.isPlaying()){
                        mInteractivePlayerView.start();
                        mediaPlayer.seekTo(pauseCurrentPosition);
                        mediaPlayer.start();
                        imageView.setBackgroundResource(R.drawable.ic_action_pause);

                    }
                } else {
                    mInteractivePlayerView.stop();
                    mediaPlayer.pause();
                    pauseCurrentPosition=mediaPlayer.getCurrentPosition();
                    imageView.setBackgroundResource(R.drawable.ic_action_play);
                }
            }
        });
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach( context );
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException( context.toString()
//                    + " must implement OnFragmentInteractionListener" );
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
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
