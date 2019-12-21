package com.example.musicapplication.ui.home;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Genre;
import com.example.musicapplication.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private HashMap<String, List<Song>> songGenreMap;
    private MainActivity mainActivity = (MainActivity) getActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateView: run");

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //get dimension of full screen
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        final int screen_height = size.y;

        songGenreMap = MainActivity.divideListSong( mainActivity.getListSong(), mainActivity.getListGenre() );

        PieceSongAdapter pieceSongAdapter = new PieceSongAdapter(getActivity(), songGenreMap, mainActivity.getListGenre());
        RecyclerView pieceItem = root.findViewById( R.id.recycler_view1 );
        pieceItem.setAdapter( pieceSongAdapter );
        pieceItem.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        //fix size of recycler view
        ViewGroup.LayoutParams rv_params = pieceItem.getLayoutParams();
        rv_params.height = screen_height - 350; // 350 or 200 or 250

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();


    }


}