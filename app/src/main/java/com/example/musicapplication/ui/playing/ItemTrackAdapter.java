package com.example.musicapplication.ui.playing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;
import com.example.musicapplication.ui.home.HomeFragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class ItemTrackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnActionClickedListener {
    private static final String TAG = "Item Adapter";
    private Context mContext;
    private SongPlayingFragment songPlayingFragment;
    private static final int USER_ACTIVITY_LAYOUT= 0;
    private static final int MUSIC_ITEM_LAYOUT= 1;
    private static MediaPlayer mediaPlayer;
    private boolean initialStage = true;
    private boolean playPause;
    int pauseCurrentPosition;
    private ProgressDialog progressDialog;
    long timer;
    private List<Song> latestSong;
    private Song playingSong;

    private InteractivePlayerView mInteractivePlayerView;
    private ImageView control;

    private static int positionPlaying;
    private static boolean isMainPlayer = false;

    private boolean isFirstLoad = true;

    String url = "https://server.hoangbk.com/api/zingmp3/download?id=ZWA86FZB&type=320";

    private List<Song> songList = new ArrayList<>();

    public ItemTrackAdapter(Context mContext, SongPlayingFragment songPlayingFragment, MediaPlayer mediaPlayer, ProgressDialog progressDialog, Song playing, List<Song> listLatest){
        this.mContext = mContext;
        this.songPlayingFragment = songPlayingFragment;
        this.mediaPlayer = mediaPlayer;
        this.progressDialog = progressDialog;
        this.playingSong = playing;
        this.latestSong = listLatest;
        this.playPause = MainActivity.isPlayPause();
        this.initialStage = MainActivity.isInitialStage();
    }

    public int getItemViewType(int position)
    {
        if(position==0)
            return USER_ACTIVITY_LAYOUT;
        else
            return MUSIC_ITEM_LAYOUT;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        ItemTrackAdapter.mediaPlayer = mediaPlayer;
    }

    public static void setPositionPlaying(int positionPlaying) {
        ItemTrackAdapter.positionPlaying = positionPlaying;
    }

    public static void setIsMainPlayer(boolean isMainPlayer) {
        ItemTrackAdapter.isMainPlayer = isMainPlayer;
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
        Log.e(TAG, "----------------error1==----------");
        if(holder.getItemViewType()== USER_ACTIVITY_LAYOUT && isFirstLoad)
        {
            isFirstLoad = false;
            MusicPlayingHolder userActivityViewHolder = (MusicPlayingHolder)holder;
            mInteractivePlayerView.setCoverURL(playingSong.getThumbnail());
            userActivityViewHolder.title.setText((playingSong.getName()));
            if(playingSong.getComposers().size() > 0){
                userActivityViewHolder.artist.setText(playingSong.getComposers().get(0).getName());}
            new MainActivity.DownloadImageTask(userActivityViewHolder.background).execute(playingSong.getThumbnail());

            if(!isMainPlayer){
                Log.e(TAG, "----------------error----------");
                mInteractivePlayerView.setMax(60);
                mInteractivePlayerView.setProgress(0);
                mInteractivePlayerView.setOnActionClickedListener(this);

                if(!initialStage){
                    mediaPlayer.reset();
                    new Player().execute(playingSong.getDownloadurl());
                }

            }else{
                long timer1 = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());
                int seconds = ((positionPlaying % (1000*60*60)) / 1000);
                if((int)(positionPlaying % (1000*60*60)) / (1000*60) > 30){
                    seconds = 0;
                }
                mInteractivePlayerView.setMax( (int) timer1 );
                mInteractivePlayerView.setProgress(seconds);
                mInteractivePlayerView.setOnActionClickedListener(this);
                pauseCurrentPosition = positionPlaying;
                if(!playPause){
                    mInteractivePlayerView.start();
                    control.setBackgroundResource(R.drawable.ic_action_pause);
                }
                isMainPlayer = false;
            }

        }
        else if(holder.getItemViewType()== MUSIC_ITEM_LAYOUT) {
            Song songItem = latestSong.get(position - 1);
            MusicItemHolder musicViewHolder = (MusicItemHolder)holder;
            musicViewHolder.songName.setText(songItem.getName());
            if (songItem.getComposers().size() > 0)
                musicViewHolder.artist.setText(songItem.getComposers().get(0).getName());

            musicViewHolder.position = position-1;
            new MainActivity.DownloadImageTask(musicViewHolder.img).execute(songItem.getThumbnail());
            musicViewHolder.NextSong = songItem;

        }

    }

    @Override
    public int getItemCount() {
        return latestSong.size() + 1;
    }

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


    class MusicPlayingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView artist;
        private TextView time;
        private ImageView background;
        private MainActivity mainActivity = (MainActivity) mContext;

        private ImageView back_button;
        protected View control_cover;

        public MusicPlayingHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.musicTitle);
            artist = (TextView)itemView.findViewById(R.id.musicArtistName);
            background = (ImageView)itemView.findViewById(R.id.background);
            mInteractivePlayerView = itemView.findViewById(R.id.interactivePlayerView);
            control = itemView.findViewById(R.id.control);
            control_cover = itemView.findViewById(R.id.control_cover);
            control_cover.setOnClickListener(this);
            back_button= itemView.findViewById(R.id.back_button);
            back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songPlayingFragment.onBackPressed();

                    if(playPause){
                        mainActivity.setPauseCurrentPosition( pauseCurrentPosition );
                        MainActivity.setPlayPause( false );
                    }else{
                        MainActivity.setPlayPause( true );
                    }

                    MainActivity.setMediaPlayer( mediaPlayer );
                    mainActivity.mainPlayerSong( playingSong );
                    mainActivity.setPosition( playingSong.getId() - 1 );

                }
            });
        }

        @Override
        public void onClick(View v) {

            if (playPause) {
                if(initialStage){
                    new Player().execute(playingSong.getDownloadurl());
                }else{
                    if (!mediaPlayer.isPlaying()){
                        mInteractivePlayerView.start();
                        mediaPlayer.seekTo(pauseCurrentPosition);
                        control.setBackgroundResource(R.drawable.ic_action_pause);
                        mediaPlayer.start();
                        MediaResume(v);
                    }

                    if(mediaPlayer==null) {
                        control.setBackgroundResource(R.drawable.ic_action_pause);
                        mInteractivePlayerView.start();
                        MediaStart(v);
                    }
                }
                playPause = false;
            } else {
                control.setBackgroundResource(R.drawable.ic_action_play);

                if (mediaPlayer.isPlaying()) {
                    mInteractivePlayerView.stop();
                    MediaPause(v);
                }

                playPause = true;
            }
        }


    }

    public class MusicItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView songName;
        protected TextView artist;
        ImageView img;
        protected Song NextSong;
        protected int position;
        View latestSongView;


        public MusicItemHolder(View itemView) {
            super(itemView);
            songName = (TextView)itemView.findViewById(R.id.textViewSongTitle);
            artist = (TextView)itemView.findViewById(R.id.textViewArtistName);
            img = (ImageView) itemView.findViewById(R.id.imageTrack);
            latestSongView = itemView.findViewById(R.id.latestSongView);
            latestSongView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            playingSong = NextSong;
//check for add to recently played
            boolean check=false;
            for (int i=0; i  < MainActivity.recentlyPlayed.size() ; i++) {
                if (playingSong != MainActivity.recentlyPlayed.get(i)){
                    check=true;
                }
                else {check=false;
                    break;}
            }
            if(check==true)
                MainActivity.recentlyPlayed.add(playingSong);

            notifyItemChanged(0);
            mediaPlayer.reset();
            isFirstLoad = true;
        }
    }
    public void MediaStart(View view){
        mediaPlayer.start();

    };
    public void MediaResume(View view){
        mediaPlayer.seekTo(pauseCurrentPosition);
        mediaPlayer.start();

    };
    public void MediaPause(View view){
        mediaPlayer.pause();
        pauseCurrentPosition=mediaPlayer.getCurrentPosition();

    };

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
                timer = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());


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
                mInteractivePlayerView.setMax((int)timer);
                mInteractivePlayerView.start();
                control.setBackgroundResource(R.drawable.ic_action_pause);

            }
            mediaPlayer.start();
            playPause = false;
            initialStage = false;
            MainActivity.setInitialStage(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
    }
}
