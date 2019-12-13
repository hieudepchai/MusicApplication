package com.example.musicapplication.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Composer;
import com.example.musicapplication.model.Genre;
import com.example.musicapplication.model.Singer;
import com.example.musicapplication.model.Song;

import java.util.List;

public class SearchedSongAdapter  extends RecyclerView.Adapter<SearchedSongAdapter.SearchedSongViewHolder>{
    private List<Song> adapter_listSong;
    private Context mContext;
    public SearchedSongAdapter (){

    }
    public SearchedSongAdapter (List<Song> listSong){
        this.adapter_listSong = listSong;
    }

    public SearchedSongAdapter(List<Song> adapter_listSong, Context mContext) {
        this.adapter_listSong = adapter_listSong;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchedSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_song_item, parent,false);
        return new SearchedSongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedSongViewHolder holder, int position) {
        Song song = adapter_listSong.get(position);
        new MainActivity.DownloadImageTask(holder.songImage).execute(song.getThumbnail());
        holder.songName.setText(song.getName());
        String genre="", singer ="", composer="";
        for(Singer s: song.getSingers()){
            singer += s.getName()+", ";
        }
        if(singer.length()>0){
            singer = singer.substring(0, singer.length()-2);
        }
        else{
            singer="unknown";
        }
        for(Genre g: song.getGenres()){
            genre += g.getName()+", ";
        }
        if(genre.length()>0){
            genre = genre.substring(0, genre.length()-2);
        }
        else{
            genre="unknown";
        }
        for(Composer c: song.getComposers()){
            composer += c.getName()+", ";
        }
        if(composer.length()>0){
            composer = composer.substring(0, composer.length()-2);
        }
        else{
            composer="unknown";
        }
        holder.songSinger.setText(singer);
        holder.songGenre.setText(genre);
        holder.songComposer.setText(composer);
    }

    @Override
    public int getItemCount() {
        return this.adapter_listSong.size();
    }

    public Context getmContext() {
        return mContext;
    }

    public class SearchedSongViewHolder extends RecyclerView.ViewHolder{
        public ImageView songImage;
        public TextView songName;
        public TextView songSinger;
        public TextView songComposer;
        public TextView songGenre;
        public SearchedSongViewHolder(@NonNull View itemView) {
            super(itemView);
            songImage = (ImageView) itemView.findViewById(R.id.searchedSongImage);
            songName = (TextView) itemView.findViewById((R.id.searchedSongName));
            songSinger = (TextView) itemView.findViewById(R.id.searchedSongSinger);
            songComposer = (TextView) itemView.findViewById(R.id.searchedSongComposer);
            songGenre = (TextView) itemView.findViewById(R.id.searchedSongGenre);
        }
    }
}
