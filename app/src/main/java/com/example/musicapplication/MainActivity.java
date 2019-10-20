package com.example.musicapplication;

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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseManager dbManager = new DatabaseManager();
    private List<Song> listSong = dbManager.getListSong();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Log.d(TAG, "onCreate: ------------------------------------------------------");

    }

    public void goToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        //transaction.replace(R.id.content_frame, fragment);

        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack("").commit();
        transaction.addToBackStack(null);
        //transaction.commit();
//                ft.add(R.id.frame, fragment).addToBackStack("").commit();
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
