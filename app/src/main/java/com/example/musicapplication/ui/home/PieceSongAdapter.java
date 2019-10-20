package com.example.musicapplication.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.R;

public class PieceSongAdapter extends RecyclerView.Adapter<PieceSongAdapter.PieceHolder> {
    private static final String TAG = "piece Adapter";
    private Context mContext;

    public PieceSongAdapter(Context mContext){
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PieceSongAdapter.PieceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.song_piece_layout, parent,false);
        return new PieceSongAdapter.PieceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PieceHolder holder, int position) {
        TextView pieceTitle = holder.pieceTitle;
        pieceTitle.setText( "Relax" );
        TextView descrip = holder.descrip;
        descrip.setText( "Popular playlists from the QUAN community" );

        LinearLayoutManager childLayoutManager = new LinearLayoutManager( holder.songItem.getContext(), LinearLayoutManager.HORIZONTAL, false);

        ItemSongAdapter songAdapter = new ItemSongAdapter(mContext);
        RecyclerView songItem = holder.songItem;
        songItem.setAdapter( songAdapter );
        songItem.setLayoutManager( childLayoutManager );
        Log.d(TAG, "-------------position: " + position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PieceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView pieceTitle;
        protected TextView descrip;
        protected RecyclerView songItem;
        View pieceViewOver;
        protected View root;

        public PieceHolder(@NonNull View itemView) {
            super( itemView );
            root = itemView;
            this.pieceTitle = itemView.findViewById( R.id.title );
            this.descrip = itemView.findViewById( R.id.description );
            this.songItem = itemView.findViewById( R.id.recycler_view );
            this.pieceViewOver = itemView.findViewById( R.id.playlist_over );
            pieceViewOver.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "piece song click----------------");
        }
    }
}
