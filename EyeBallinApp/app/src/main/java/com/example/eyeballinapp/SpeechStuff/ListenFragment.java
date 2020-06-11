package com.example.eyeballinapp.SpeechStuff;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;


public class ListenFragment extends Fragment {

    TextToSpeech mTts;
    private final String UTTER_ID = "speak_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mTts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SpeechResult.get(getContext()).getSpeechText()
                            , TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
                    while(mTts.isSpeaking()) {}
                    startListening();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        //Close the Text to Speech Library
        if(mTts != null) {

            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    public void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Destination");
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == -1 && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    SpeechResult.get(getActivity()).setMessage(result.get(0));
                    break;
                }
            }
        }
        getActivity().finish();
    }

    public void startSpeaking(String message) {
        mTts.speak(message, TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
        while(mTts.isSpeaking()) {}
    }

}

