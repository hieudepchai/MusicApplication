package com.example.musicapplication.ui.playing;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class ItemTrackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "Item Adapter";
    private Context mContext;
    private static final int USER_ACTIVITY_LAYOUT= 0;
    private static final int MUSIC_ITEM_LAYOUT= 1;
    MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    private boolean playPause;
    int pauseCurrentPosition;
    private ProgressDialog progressDialog;
    String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3";

    private List<Song> songList = new ArrayList<>();

    public ItemTrackAdapter(Context mContext, MediaPlayer mediaPlayer, ProgressDialog progressDialog){
        this.mContext = mContext;
        this.mediaPlayer = mediaPlayer;
        this.progressDialog = progressDialog;
    }


    public int getItemViewType(int position)
    {
        if(position==0)
            return USER_ACTIVITY_LAYOUT;
        else
            return MUSIC_ITEM_LAYOUT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType==USER_ACTIVITY_LAYOUT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_playing,parent,false);
            viewHolder = new MusicPlayingHolder(view);

        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_song_item,parent,false);
            viewHolder = new MusicItemHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()== USER_ACTIVITY_LAYOUT)
        {
            MusicPlayingHolder userActivityViewHolder = (MusicPlayingHolder)holder;
            userActivityViewHolder.title.setText("Anh yeu em");
            userActivityViewHolder.artist.setText("Quan Nguyen");
            userActivityViewHolder.background.setImageResource(R.drawable.one_direction_blur);
        }
        else {

            MusicItemHolder musicViewHolder = (MusicItemHolder)holder;
            musicViewHolder.songName.setText("Ten gi nghi hoai ko ra");
            musicViewHolder.artist.setText("Ten ca si la gi?");
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }




    class MusicPlayingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView artist;
        private ImageView background;
        private InteractivePlayerView mInteractivePlayerView;
        private ImageView control;

        public MusicPlayingHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.musicTitle);
            artist = (TextView)itemView.findViewById(R.id.musicArtistName);
            background = (ImageView)itemView.findViewById(R.id.background);
            mInteractivePlayerView = itemView.findViewById(R.id.interactivePlayerView);
            control = itemView.findViewById(R.id.control);
            control.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mInteractivePlayerView.setMax(60);
            mInteractivePlayerView.setProgress(0);
            mInteractivePlayerView.setOnActionClickedListener((OnActionClickedListener) this);

            if (!playPause) {
                control.setBackgroundResource(R.drawable.ic_action_pause);
                mInteractivePlayerView.start();

                if (initialStage) {
                    new Player().execute(url);

                } else {
                    if (!mediaPlayer.isPlaying()){
                        mInteractivePlayerView.start();
                        mediaPlayer.seekTo(pauseCurrentPosition);
                        mediaPlayer.start();}

                    if(mediaPlayer==null) {
                        mInteractivePlayerView.start();
                        mediaPlayer.start();
                    }
                    playPause = true;
                }
            } else {
                control.setBackgroundResource(R.drawable.ic_action_play);

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mInteractivePlayerView.stop();
                    pauseCurrentPosition=mediaPlayer.getCurrentPosition();
                }

                playPause = false;
            }
        }

        class Player extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... strings) {
                Boolean prepared = false;

                try {
                    mediaPlayer.setDataSource(strings[0]);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            initialStage = true;
                            playPause = false;
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });

                    mediaPlayer.prepare();
                    prepared = true;

                } catch (Exception e) {
                    Log.e("MyAudioStreamingApp", e.getMessage());
                    prepared = false;
                }

                return prepared;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (progressDialog.isShowing()) {
                    progressDialog.cancel();

                }

                mediaPlayer.start();
                initialStage = false;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
        }
    }

    class MusicItemHolder extends RecyclerView.ViewHolder{

        private TextView songName;
        protected TextView artist;



        public MusicItemHolder(View itemView) {
            super(itemView);
            songName = (TextView)itemView.findViewById(R.id.textViewSongTitle);
            artist = (TextView)itemView.findViewById(R.id.textViewArtistName);
        }

    }

//    public void onBindViewHolder(@NonNull ItemTrackAdapter.ItemHolder itemHolder, int i) {
//
//        TextView mMusicName = itemHolder.title;
//        mMusicName.setText( "Nhac ten gi ke bo?" );
//        TextView mAuthor = itemHolder.author;
//        mAuthor.setText( "Dung Tran" );
//
//    }

}
