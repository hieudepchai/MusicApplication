package com.example.musicapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.musicapplication.model.Composer;
import com.example.musicapplication.model.Genre;
import com.example.musicapplication.model.Singer;
import com.example.musicapplication.model.Song;
import com.example.musicapplication.service.RetrofitInterface;
import com.example.musicapplication.service.RetrofitService;
import com.example.musicapplication.ui.playing.ItemTrackAdapter;
import com.example.musicapplication.ui.playing.SongPlayingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static List<Song> listSong;
    private static List<Singer> listSinger;
    private static List<Genre> listGenre;
    private static List<Composer> listComposer;
    public static List<Song> recentlyPlayed = new ArrayList<>();
    private static View mainPlayer;
    private static View navigationBar;
    private static boolean playPause = true;  //play: true     pause: false
    private static boolean initialStage = true;
    private static MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private int pauseCurrentPosition;
    private int position;
    private Song songItem;

    private ImageView playStop;

    public MainActivity() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ------------------------------------------------------");
        RetrofitInterface retrofit_interface = RetrofitService.getService();
        Call<List<Song>> callSong = retrofit_interface.getSong();
        final Call<List<Singer>> callSinger = retrofit_interface.getSinger();
        final Call<List<Genre>> callGenre = retrofit_interface.getGenre();
        final Call<List<Composer>> callComposer = retrofit_interface.getComposer();
        callSong.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response){
                listSong = response.body();;
                Log.d("listSong: ", String.valueOf(listSong.size()));
                callSinger.enqueue(new Callback<List<Singer>>() {
                    @Override
                    public void onResponse(Call<List<Singer>> call, Response<List<Singer>> response) {
                        listSinger = response.body();
                        Log.d("listSinger: ", String.valueOf(listSinger.size()));
                         callGenre.enqueue(new Callback<List<Genre>>() {
                             @Override
                             public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                                listGenre = response.body();
                                 Log.d("listGenre: ", String.valueOf(listGenre.size()));
                                 callComposer.enqueue(new Callback<List<Composer>>() {
                                     @Override
                                     public void onResponse(Call<List<Composer>> call, Response<List<Composer>> response) {
                                         listComposer=response.body();
                                         Log.d("listComposer: ", String.valueOf(listComposer.size()));
                                         loadUI();
                                     }

                                     @Override
                                     public void onFailure(Call<List<Composer>> call, Throwable t) {

                                     }
                                 });
                             }

                             @Override
                             public void onFailure(Call<List<Genre>> call, Throwable t) {

                             }
                         });
                    }

                    @Override
                    public void onFailure(Call<List<Singer>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });


    }

    public static List<Song> getListSong() {
        return listSong;
    }

    public static List<Genre> getListGenre() {
        return listGenre;
    }

    public static List<Singer> getListSinger() {
        return listSinger;
    }

    public static List<Composer> getListComposer() {
        return listComposer;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MainActivity.mediaPlayer = mediaPlayer;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPauseCurrentPosition(int pauseCurrentPosition) {
        this.pauseCurrentPosition = pauseCurrentPosition;
    }

    public static boolean isPlayPause() {
        return playPause;
    }

    public static void setPlayPause(boolean playPause) {
        MainActivity.playPause = playPause;
    }

    public static boolean isInitialStage() {
        return initialStage;
    }

    public static void setInitialStage(boolean initialStage) {
        MainActivity.initialStage = initialStage;
    }

    public static void setListSong(List<Song> listSong) {
        MainActivity.listSong = listSong;
    }

    public static List<Song> getLastestSong(){ //last 30 songs
        List<Song> latestSong = new ArrayList<>(  );
        for(int i=listSong.size()-1; i>=listSong.size()-30; i--){
            latestSong.add(listSong.get( i ));
        }
        return latestSong;
    }

    private void mainPlayerSetup(Song songItem){

        new DownloadImageTask( (ImageView) findViewById( R.id.mainPlayerImg ) ).execute( songItem.getThumbnail() );
        TextView mainPlayerName = findViewById( R.id.mainPlayerName );
        mainPlayerName.setText(songItem.getName());
        String singers = "";
        for(int j=0; j<songItem.getSingers().size(); j++){
            if(j == songItem.getSingers().size()-1){
                singers += songItem.getSingers().get(j).getName();
                break;
            }
            singers += songItem.getSingers().get(j).getName() + ", ";
        }
        TextView mainPlayerSinger = findViewById( R.id.mainPlayerSinger );
        mainPlayerSinger.setText( singers );
        //new downloadMusicTask().execute(songItem.getDownloadurl());

    }

    private void mainPlayerPosition(){ //set main player by position in listSong
        songItem = listSong.get(position);
        mainPlayerSetup( songItem );
    }

    public void mainPlayerSong(Song songPlaying){ //set main Player by songPlaying transferring from SongPlayingFragment
        songItem = songPlaying;
        mainPlayerSetup( songItem );

        if(!playPause){
            playStop.setBackgroundResource( R.drawable.ic_play_arrow_black_24dp );
            playPause = true;
        }else{
            playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
            playPause = false;
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                Log.e(TAG, "media player: reset---------------");
                if(position == listSong.size()-1){
                    initialStage = true;
                    playPause = true;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    playStop.setBackgroundResource( R.drawable.ic_play_arrow_black_24dp );
                }else{
                    position += 1;
                    mediaPlayer.reset();
                    mainPlayerPosition();
                    new downloadMusicTask().execute(songItem.getDownloadurl());

                    playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
                    //mediaPlayer.start();

                    playPause = false;
                }

            }
        });
    }

    private void mainPlayerNextPlayBack(){
        ImageView moveNext = findViewById( R.id.mainPlayerSkipNext );
        ImageView moveBack = findViewById( R.id.mainPlayerPrevBack );
        playStop = findViewById( R.id.mainPlayerPlay );

        moveNext.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(position < listSong.size())
                {
                    position += 1;
                    mediaPlayer.reset();
                    mainPlayerPosition();
                    new downloadMusicTask().execute(songItem.getDownloadurl());

                    playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
                    //mediaPlayer.start();

                    playPause = false;
                }

            }
        } );
        moveBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(position > 0)
                {
                    position -= 1;
                    mediaPlayer.reset();
                    mainPlayerPosition();
                    new downloadMusicTask().execute(songItem.getDownloadurl());

                    playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
                    //mediaPlayer.start();

                    playPause = false;
                }

            }
        } );

        playStop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playPause){
                    playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
                    playPause = false;
                    if(initialStage){
                        new downloadMusicTask().execute(songItem.getDownloadurl());
                    }else{
                        if (!mediaPlayer.isPlaying()){
                            mediaPlayer.seekTo(pauseCurrentPosition);
                            mediaPlayer.start();
                        }

                        if(mediaPlayer==null) {
                            mediaPlayer.start();
                        }

                    }

                }else{
                    playStop.setBackgroundResource( R.drawable.ic_play_arrow_black_24dp );
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        pauseCurrentPosition=mediaPlayer.getCurrentPosition();
                    }
                    playPause = true;
                }
            }
        } );

        mainPlayer.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<Song> latestSong = getLastestSong();

                ItemTrackAdapter.setIsMainPlayer( true );
                ItemTrackAdapter.setMediaPlayer( mediaPlayer );
                ItemTrackAdapter.setPositionPlaying( mediaPlayer.getCurrentPosition() );

                uncollapseFragment( SongPlayingFragment.newInstance( songItem, latestSong ) );

            }
        } );
    }

    public void loadUI(){
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_stream, R.id.navigation_library, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        progressDialog = new ProgressDialog( MainActivity.this );
        mainPlayer = findViewById( R.id.mainPlayer );
        navigationBar = findViewById( R.id.bottom_navigation_parent );
        position = 3;
        mainPlayerPosition();
        mainPlayerNextPlayBack();
    }
    public void goToFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.nav_host_fragment, fragment) // replace flContainer
                .addToBackStack(null)
                .commit();
    }

    public void uncollapseFragment(Fragment fragment){
        if(fragment.isHidden()){
            Log.e(TAG, "playing fragment: show");
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom )
                    .show(fragment)
                    .commit();
        }else{
            Log.e(TAG, "playing fragment: initial");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom )
                    .replace(R.id.nav_host_fragment, fragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }

        mainPlayer.setVisibility( View.GONE );
        navigationBar.setVisibility( View.GONE );
    }

    public void hideMainPlayer(){
        mainPlayer.setVisibility( View.GONE );
        if(!playPause){
            playStop.setBackgroundResource( R.drawable.ic_play_arrow_black_24dp );
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                pauseCurrentPosition=mediaPlayer.getCurrentPosition();
            }
            playPause = true;
        }
    }
    public static void showMainPlayer(){
        mainPlayer.setVisibility( View.VISIBLE );
        navigationBar.setVisibility( View.VISIBLE );
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class downloadMusicTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared;

            try {
                if(mediaPlayer != null){
                    Log.e(TAG, "media player: change music--======---------------");
                    //mediaPlayer.stop();
                    //mediaPlayer.reset();
                }
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        Log.e(TAG, "media player: reset---------------");
                        if(position == listSong.size()-1){
                            initialStage = true;
                            playPause = true;
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            playStop.setBackgroundResource( R.drawable.ic_play_arrow_black_24dp );
                        }else{
                            position += 1;
                            mediaPlayer.reset();
                            mainPlayerPosition();
                            new downloadMusicTask().execute(songItem.getDownloadurl());

                            playStop.setBackgroundResource( R.drawable.ic_pause_black_24dp );
                            //mediaPlayer.start();

                            playPause = false;
                        }

                    }
                });
                //mediaPlayer.setNextMediaPlayer( new MediaPlayer() );
                mediaPlayer.prepare();
                prepared = true;


            } catch (Exception e) {
                Log.e(TAG, "download music error: " + e.getMessage());
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


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle ------ ", "Main Activity: onStart()");

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle ------ ", "Main Activity: onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle ------ ", "Main Activity: onPause()");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle ------ ", "Main Activity: onStop()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle ------ ", "Main Activity: onDestroy()");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle ------ ", "Main Activity: onRestart()");

    }
}