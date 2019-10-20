package com.example.musicapplication.ui.home;

import android.os.Bundle;
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

import com.example.musicapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateView: run");

//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        PieceSongAdapter pieceSongAdapter = new PieceSongAdapter(getActivity());
        RecyclerView pieceItem = root.findViewById( R.id.recycler_view1 );
        pieceItem.setAdapter( pieceSongAdapter );
        pieceItem.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("HomeFragment", "onActivityCreated: run");
//        getView().findViewById(R.id.navigation_stream).setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("HomeFragment", "click Stream");
//            }
//        } );
    }

//    private void runFadeInAnimation() {
//        Animation a = AnimationUtils.loadAnimation(this, R.animator.fade_in);
//        a.reset();
//        LinearLayout ll = (LinearLayout) findViewById(R.id.yourviewhere);
//        ll.clearAnimation();
//        ll.startAnimation(a);
//    }
}