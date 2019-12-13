package com.example.musicapplication.ui.search;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Composer;
import com.example.musicapplication.model.Genre;
import com.example.musicapplication.model.Singer;
import com.example.musicapplication.model.Song;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SearchFragment extends Fragment {
    private MainActivity mainActivity  =  (MainActivity) getActivity();
    private List<Song> listSong = mainActivity.getListSong();
    private List<Singer> listSinger = mainActivity.getListSinger();
    private List<Genre> listGenre = mainActivity.getListGenre();
    private List<Composer> listComposer = mainActivity.getListComposer();
    private HashMap<String, List<Song>> mapGenreSong = new HashMap<>();
    private HashMap<String, List<Song>> mapSingerSong = new HashMap<>();
    private HashMap<String, List<Song>> mapComposerSong = new HashMap<>();
    private RecyclerView rvSearchResult;
    private RecyclerView rvSearchResult2;
    private SearchView searchView;
    private TextView tvSearchResult;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_search, container, false);
        final Spinner spinnerDropdown = (Spinner) root.findViewById(R.id.spinnerSearch);
//       init
        for(Singer singer: listSinger){
            mapSingerSong.put(singer.getName(), new ArrayList<Song>());
        }
        for(Genre genre: listGenre){
            mapGenreSong.put(genre.getName(), new ArrayList<Song>());
        }
        for(Composer composer: listComposer){
            mapComposerSong.put(composer.getName(), new ArrayList<Song>());
        }
//
        for (Song song: listSong){
            for(Singer singer: song.getSingers()){
                mapSingerSong.get(singer.getName()).add(song);
            }
            for(Genre genre :song.getGenres()){
                mapGenreSong.get(genre.getName()).add(song);
            }
            for(Composer composer: song.getComposers()){
                mapComposerSong.get(composer.getName()).add(song);
            }
        }
//        find
        searchView = (SearchView) root.findViewById(R.id.search_view);
        rvSearchResult = (RecyclerView) root.findViewById(R.id.rvSearchResult);
        rvSearchResult2 = (RecyclerView) root.findViewById(R.id.rvSearchResult2);
        tvSearchResult = (TextView) root.findViewById(R.id.tvSearchResult);
//      add spinner type
        ArrayList<String> search_type = new ArrayList<>();
        search_type.add("Song");
        search_type.add("Singer");
//        search_type.add("Genre");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, search_type);
        spinnerDropdown.setAdapter(spinnerAdapter);
//      usable screen width, height
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        final int screen_height = size.y;
        searchView.clearFocus();
//        add search view event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//               clear recycler view
                rvSearchResult.setAdapter(null);
                rvSearchResult2.setAdapter(null);
                searchView.clearFocus();
                query = query.toLowerCase();
//                set height recycler view
                int searchview_height = searchView.getHeight();
                ViewGroup.LayoutParams rv_params = rvSearchResult.getLayoutParams();
                rv_params.height = screen_height -searchview_height-420;
                rvSearchResult.setLayoutParams(rv_params);
                rvSearchResult2.setLayoutParams(rv_params);
                String type = spinnerDropdown.getSelectedItem().toString();
//                result type
                List<Song>  type_song_result = new ArrayList<>();
                switch (type){
                    case "Singer":{
                        List<String> foundSingerName = new ArrayList<>();
                        List<String> searched_listSinger = new ArrayList<>();
                        for(Singer singer: listSinger){
                            String org_singername = singer.getName().toLowerCase();
                            String norm_singername = removeAccent(org_singername);
                            if(org_singername.contains(query) || norm_singername.contains(query)){
                                foundSingerName.add(singer.getName());
                            }
                        }
//                        rvSerachResult2 adatper
                        if (foundSingerName.size() > 0 ){
                            SearchedBySingerAdapter searchedBySingerAdapter = new SearchedBySingerAdapter(foundSingerName, mapSingerSong, getActivity());
                            rvSearchResult2.setAdapter(searchedBySingerAdapter);
                            rvSearchResult2.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                        tvSearchResult.setText("Found "+ foundSingerName.size()+" singer(s).");
                        break;
                    }
                    case "Song":{
                        for(Song song: listSong){
                            String org_songname = song.getName().toLowerCase();
                            String norm_songname = removeAccent(org_songname);
                            if (org_songname.contains(query) || norm_songname.contains(query)){
                                type_song_result.add(song);
                            }
                        }
                        //set height for recycler view

//                        input recycler view
                        if (type_song_result.size() > 0){
                            SearchedSongAdapter searchedSongAdapter = new SearchedSongAdapter(new ArrayList<Song>(type_song_result), getActivity());
                            rvSearchResult.setAdapter(searchedSongAdapter);
                            rvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                        tvSearchResult.setText("Found "+ type_song_result.size() +" result(s).");
                        break;
                    }
                    case "Gerne":{
                        break;
                    }
                    case "Composer":{
                        break;
                    }

                }
                type_song_result.clear();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return root;
    }


    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
