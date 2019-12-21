package com.example.musicapplication.ui.library;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;
import com.example.musicapplication.ui.home.ItemSongAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        List<Song> recentSong = new ArrayList<>( MainActivity.recentlyPlayed );
        Collections.reverse(recentSong);
        ItemSongAdapter songAdapter = new ItemSongAdapter(getActivity(), "recentPlay", recentSong);
        RecyclerView songItem = root.findViewById( R.id.recently_recyclerview );
        songItem.setAdapter( songAdapter );
        LinearLayoutManager childLayoutManager = new LinearLayoutManager( songItem.getContext(), LinearLayoutManager.HORIZONTAL, false);

        songItem.setLayoutManager( childLayoutManager );

        return root;
    }
}