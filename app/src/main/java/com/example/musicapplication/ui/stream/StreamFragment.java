package com.example.musicapplication.ui.stream;

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

import com.example.musicapplication.MainActivity;
import com.example.musicapplication.R;

public class StreamFragment extends Fragment {

    private StreamViewModel streamViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        streamViewModel =
                ViewModelProviders.of(this).get(StreamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stream, container, false);
        final TextView textView = root.findViewById(R.id.text_stream);
        streamViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        root.findViewById( R.id.publish ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).goToFragment(PublishFragment.newInstance());
            }
        } );

        return root;
    }

}