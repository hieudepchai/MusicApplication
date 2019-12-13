package com.example.musicapplication.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchedBySingerAdapter extends  RecyclerView.Adapter<SearchedBySingerAdapter.SearchBySingerViewHolder> {
    private List<String> foundSingerName;
    private HashMap<String, List<Song>> mapSingerSong = new HashMap<>();
    private Context mContext;
    public SearchedBySingerAdapter() {
    }

    public SearchedBySingerAdapter(List<String> foundSingerName, HashMap<String, List<Song>> mapSingerSong) {
        this.foundSingerName = foundSingerName;
        this.mapSingerSong = mapSingerSong;
    }

    public SearchedBySingerAdapter(List<String> foundSingerName, HashMap<String, List<Song>> mapSingerSong, Context mContext) {
        this.foundSingerName = foundSingerName;
        this.mapSingerSong = mapSingerSong;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchBySingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv2_item, parent,false);
        return new SearchBySingerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBySingerViewHolder holder, int position) {
        String singer_name = foundSingerName.get(position);
        List<Song> foundListSong = new ArrayList<>(mapSingerSong.get(singer_name));
        holder.tvResultItem.setText(singer_name);
        holder.tvResultNumber.setText(foundListSong.size()+" song(s)");
        SearchedSongAdapter searchedSongAdapter = new SearchedSongAdapter(foundListSong);
        holder.rvRv2.setAdapter(searchedSongAdapter);
        LinearLayoutManager childLayoutManager = new LinearLayoutManager( holder.rvRv2.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.rvRv2.setLayoutManager(childLayoutManager);

    }

    @Override
    public int getItemCount() {
        return foundSingerName.size();
    }

    public class SearchBySingerViewHolder extends RecyclerView.ViewHolder {
        public TextView tvResultItem;
        public TextView tvResultNumber;
        public RecyclerView rvRv2;
        public SearchBySingerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResultItem = (TextView) itemView.findViewById(R.id.tvResultItem);
            tvResultNumber = (TextView) itemView.findViewById(R.id.tvResultNumber);
            rvRv2 = (RecyclerView) itemView.findViewById(R.id.rvRv2);
        }
    }
}
