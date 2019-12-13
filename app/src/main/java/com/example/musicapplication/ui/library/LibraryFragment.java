package com.example.musicapplication.ui.library;

import android.os.Bundle;
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
import com.example.musicapplication.ui.home.ItemSongAdapter;

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

        libraryViewModel =
                ViewModelProviders.of(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        final TextView textView = root.findViewById(R.id.text_library);
        libraryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ItemSongAdapter songAdapter = new ItemSongAdapter(getActivity(), MainActivity.recentlyPlayed);
        RecyclerView songItem = root.findViewById( R.id.recently_recyclerview );
        songItem.setAdapter( songAdapter );
        LinearLayoutManager childLayoutManager = new LinearLayoutManager( songItem.getContext(), LinearLayoutManager.HORIZONTAL, false);

        songItem.setLayoutManager( childLayoutManager );

        return root;
    }
}