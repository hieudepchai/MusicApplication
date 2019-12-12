package com.example.musicapplication.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.model.Song;
import com.example.musicapplication.ui.library.LibraryFragment;
import com.example.musicapplication.ui.playing.SongPlayingFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemSongAdapter extends RecyclerView.Adapter<ItemSongAdapter.ItemHolder> {
    private static final String TAG = "Item Adapter";
    private Context mContext;
    private List<Song> songList;

    public ItemSongAdapter(Context mContext, List<Song> listSong){
        this.mContext = mContext;
        this.songList = listSong;
    }

    @NonNull
    @Override
    public ItemSongAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_playlist, parent,false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSongAdapter.ItemHolder itemHolder, int i) {
        // get item song ith
        final Song songItem = songList.get(i);
        Log.d(TAG, "position: " + i);

        new MainActivity.DownloadImageTask(itemHolder.art).execute(songItem.getThumbnail());
        itemHolder.title.setText( songItem.getName() );

        TextView mAuthor = itemHolder.author;
        String singers = "";
        for(int j=0; j<songItem.getSingers().size(); j++){
            if(j == songItem.getSingers().size()-1){
                singers += songItem.getSingers().get(j).getName();
                break;
            }
            singers += songItem.getSingers().get(j).getName() + ", ";
        }
        mAuthor.setText( singers );

        itemHolder.playingSong = songItem;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView title;
        protected TextView author;
        protected ImageView art;
        View view_over;
        protected View root;
        protected Song playingSong;
        private List<Song> latestSong;
        MainActivity main = (MainActivity) mContext;

        public ItemHolder(@NonNull View itemView) {
            super( itemView );
            root = itemView;
            this.title = itemView.findViewById( R.id.musicName);
            this.author = itemView.findViewById(R.id.author);
            this.art = itemView.findViewById(R.id.song_image);
            view_over = itemView.findViewById(R.id.song_over);
            view_over.setOnClickListener(this);
            //view_over.setOnTouchListener(this);

            latestSong = main.getLastestSong();
        }

        @Override
        public void onClick(View v) {
            //TODO: transfer playing song and latest song to playing UI
            Log.d(TAG, "song click----------------");
            main.goToFragment( SongPlayingFragment.newInstance());
        }
    }

}
