package com.example.musicapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.musicapplication.model.DatabaseManager;
import com.example.musicapplication.model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Song> listSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ------------------------------------------------------");
        new GetDataFirebaseAPITask().execute();
        ((MyApplication) this.getApplication()).addRealtimeUpdate(new MyApplication.MyAppCallback(){

            @Override
            public void SongCallBack(List<Song> callbackListSong) {
                if(callbackListSong.size()!=0){
                    listSong.clear();
                    listSong.addAll(callbackListSong);
                }
            }
        });
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

    public class GetDataFirebaseAPITask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            //call api to get json string from firebase
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL("https://mymusic-0000-255417.firebaseio.com/songs.json");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output = br.readLine();
                return output;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "";
            } catch (ProtocolException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //get the result from doInBackground and convert it to List<Song> using Gson
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            listSong = gson.fromJson(s, new TypeToken<List<Song>>(){}.getType());
            //load UIT
            loadUI();
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
