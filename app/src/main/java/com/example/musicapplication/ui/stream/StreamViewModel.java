package com.example.musicapplication.ui.stream;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StreamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StreamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stream fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}