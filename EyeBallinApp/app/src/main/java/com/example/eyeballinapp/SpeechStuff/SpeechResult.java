package com.example.eyeballinapp.SpeechStuff;

import android.content.Context;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

public class SpeechResult {

    private static SpeechResult sSpeechResult;
    private Context mContext;
    private CustomLocation userLocation;
    private String mMessage;
    private String mSpeech;

    public SpeechResult(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SpeechResult get(Context context) {
        if(sSpeechResult == null) {
            sSpeechResult = new SpeechResult(context);
        }
        return sSpeechResult;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setSpeechText(String text) {
        mSpeech = text;
    }

    public String getSpeechText() {
        return mSpeech;
    }

    public void setLastLocation(CustomLocation loc) {
        userLocation = loc;
    }

    public CustomLocation getLastLocation() {
        return userLocation;
    }
}
