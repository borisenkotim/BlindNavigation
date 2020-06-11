package com.example.eyeballinapp.SpeechStuff;

import androidx.fragment.app.Fragment;

public class ListenActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListenFragment();
    }
}
