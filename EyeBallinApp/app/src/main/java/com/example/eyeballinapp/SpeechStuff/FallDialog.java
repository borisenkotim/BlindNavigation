package com.example.eyeballinapp.SpeechStuff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.eyeballinapp.R;

// FIXME: 11/25/2019 A possible problem would be that if a fall is detected, all the other navigation
// activities will be stopped. Not sure if this will happen yet since were manipulating different
// activities that stop and resume on top of each other?
public class FallDialog extends DialogFragment{

    private final int REQUEST_LISTENER = 0;
    private Button stopBtn;
    private CountDownTimer countTimer;
    long interval;
    boolean sendText = true;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.alert_layout, null);

        stopBtn = v.findViewById(R.id.stop_button);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlert();
            }
        });

        interval = 11000;

        countTimer = new CountDownTimer(interval, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                interval = millisUntilFinished;
                if (sendText)
                    updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (sendText)
                    sendAlert();
            }
        }.start();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v);
        builder.setMessage("ALERT").setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        //startListener("Emergency contacts will be notified in 10 seconds");
        //SpeakActivity speak = new SpeakActivity(getContext(), "Emergency contacts will be notified in 10 seconds");

        return builder.create();
    }

    private void updateCountDownText(){
        int seconds = (int) (interval /1000);
        String timeLeft = String.valueOf(seconds);
    }


    private void startTimer() {

    }

    public void stopAlert(){
        sendText = !sendText;
        countTimer.cancel();
    }



    private void sendAlert(){
        String phoneNumber = "4256266630";
        String smsMessage = "Fall Detected!";

        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(phoneNumber, null, smsMessage, null, null);
        } catch(Exception e) {
            //Toast.makeText(testContext, "Error with sendTextMessage", Toast.LENGTH_SHORT).show();
        }
    }

    public void startListener(String message) {
        Intent myIntent = new Intent(getActivity().getApplicationContext(), ListenActivity.class);
        SpeechResult.get(getActivity().getApplicationContext()).setSpeechText(message);
        startActivityForResult(myIntent, REQUEST_LISTENER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LISTENER: {
                String testMessage = SpeechResult.get(getContext()).getMessage();
                break;
            }
        }
    }

}


