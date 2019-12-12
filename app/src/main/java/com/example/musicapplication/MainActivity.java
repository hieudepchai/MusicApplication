package com.example.musicapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ------------------------------------------------------");
        RetrofitInterface retrofit_interface = RetrofitService.getService();
        Call<List<Song>> callSong = retrofit_interface.getSong();
        final Call<List<Singer>> callSinger = retrofit_interface.getSinger();
        final Call<List<Genre>> callGenre = retrofit_interface.getGenre();
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
                                 loadUI();
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

    public static void setListSong(List<Song> listSong) {
        MainActivity.listSong = listSong;
    }

    public static List<Song> getLastestSong(){ //last 20 songs
        List<Song> latestSong = new ArrayList<>(  );
        for(int i=listSong.size()-1; i>=listSong.size()-20; i--){
            latestSong.add(listSong.get( i ));
        }
        return latestSong;
    }

    private void mainPlayerSetUp(){

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
    }
    public void goToFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.nav_host_fragment, fragment) // replace flContainer
                .addToBackStack(null)
                .commit();
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