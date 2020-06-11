package com.example.eyeballinapp.SpeechStuff;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SpeakActivity {

    private Context mContext;
    TextToSpeech mTts;
    private final String UTTER_ID = "speak_id";

    public SpeakActivity(Context context, final String sentence) {
        mContext = context;
        mTts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
                    //while(mTts.isSpeaking()) {}
                }
            }
        });
    }

    public void speak(String sentence) {
        mTts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
        //while(mTts.isSpeaking()) {}
    }




    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }
}
