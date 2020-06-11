/*
* Description: The navigation will start here, basically all the map stuff and graph nodes whatever
*              will happen here.
* */
package com.example.eyeballinapp.SpeechStuff;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eyeballinapp.EventBus.OnButtonClickedMessage;
import com.example.eyeballinapp.MainActivity;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;
import com.example.eyeballinapp.MapStuff.Navigation.Navigation;
import com.example.eyeballinapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.eyeballinapp.MainActivity.REQUEST_LISTENER;


public class NavigationActivity extends AppCompatActivity implements SensorEventListener {


    public static final int REQUEST_LISTENER_END = 1;
    private SensorManager snsmgr;
    private Sensor sns;
    long interval;

    private SpeechParser parser;

    private CountDownTimer countTimer;

    boolean notified = false;
    boolean sendText = true;

    private Button mLeftButton, mRightButton, mForwardButton, mBackButton, mElevatorUp, mElevatorDown;

    private ImageView mDirectionArrow;

    private int adapterPosition;
    private RecyclerView mRecyclerView;
    private DirectionAdapter mAdapter;
    private Navigation nav;
    private TextView mCurrentStepDistance, mXLocation, mYLocation;
    private DecimalFormat formatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_path_layout);

        formatter = new DecimalFormat("#.##");
        Intent intent = getIntent();

        String destination = intent.getStringExtra("DESTINATION");

        nav = new Navigation(getApplicationContext(), destination);

        snsmgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(NavigationActivity.this, sns, SensorManager.SENSOR_DELAY_NORMAL);

        mForwardButton = findViewById(R.id.up_button);
        mRightButton = findViewById(R.id.right_button);
        mBackButton = findViewById(R.id.down_button);
        mLeftButton = findViewById(R.id.left_button);
        mElevatorUp = findViewById(R.id.elevator_up);
        mElevatorDown = findViewById(R.id.elevator_down);

        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("forward");
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("right");
            }
        });

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("left");
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("back");
            }
        });

        mElevatorDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("down");
            }
        });

        mElevatorUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("up");
            }
        });

        mDirectionArrow = findViewById(R.id.arrow_img);

        mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_forward));

        mXLocation = findViewById(R.id.xLocation);

        mYLocation = findViewById(R.id.yLocation);

        getSupportActionBar().hide();

        mCurrentStepDistance = findViewById(R.id.distance_text);

        mRecyclerView = findViewById(R.id.step_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        updateUI();
    }

    public void fallDetected() {

        interval = 11000;

        countTimer = new CountDownTimer(interval, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                interval = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                if (sendText) sendAlert();
            }
        }.start();

        startListener(getString(R.string.notify_fall),REQUEST_LISTENER);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xSquared = sensorEvent.values[0] * sensorEvent.values[0];
        float ySquared = sensorEvent.values[1] * sensorEvent.values[1];
        float zSquared = sensorEvent.values[2] * sensorEvent.values[2];
        double xyzSRoot = Math.sqrt(xSquared + ySquared + zSquared);


        if(xyzSRoot > 15 && !notified) {
            notified = true;
            fallDetected();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    private void handleAlert(String message) {
        //probably parse message
        if(message.equals("cancel")) {
            stopText();
            SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.notify_cancel));
        }
        else {
            sendAlert();
        }
    }

    private void sendAlert() {
        String phoneNumber = "4256266630";
        String smsMessage = getString(R.string.notify_text);

        SmsManager sms = SmsManager.getDefault();
        try {
            SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.notify_contacts));
            sms.sendTextMessage(phoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "Text sent", Toast.LENGTH_SHORT).show();
            //say someone has been notified.
            sendText = false;
            return;
        } catch(Exception e) {
            //no Text Permission
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            //Log.println(1, "TEST", e.toString());
        }

    }

    public void stopText(){
        sendText = !sendText;
        countTimer.cancel();
        notified = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onButtonClickedEvent(OnButtonClickedMessage message) {
        if(message.getMessage().equals("UPDATE")) {
            updateUI();
            switch(message.getDirection()) {
                case "forward": mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_forward));
                    break;
                case "back": mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_down));
                    break;
                case "right": mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_right));
                    break;
                case "left": mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_left));
                    break;
                case "elevator": mDirectionArrow.setImageDrawable(getDrawable(R.drawable.ic_forward));
            }
        }
        if(message.getMessage().equals("CHANGED")) {
            mAdapter.notifyDataSetChanged();
            updateUI();
        }

        if(message.getMessage().equals("FINISHED")) {
            startListener("Say destination to go again, or say stop",REQUEST_LISTENER_END);
            parser = new SpeechParser();
        }


    }

    public void startListener(String message,int code) {
        Intent myIntent = new Intent(NavigationActivity.this, ListenActivity.class);
        SpeechResult.get(getApplicationContext()).setSpeechText(message);
        startActivityForResult(myIntent, code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LISTENER: {
                String testMessage = SpeechResult.get(getApplicationContext()).getMessage();
                handleAlert(testMessage);
                break;
            }
            case REQUEST_LISTENER_END: {
                String testMessage = SpeechResult.get(getApplicationContext()).getMessage();
                if(testMessage == null)
                    testMessage = "repeat";
                if(testMessage == "navigate") {
                    EyeBallinMap map = new EyeBallinMap(this);
                    if(!map.setDestination(parser.getDestination()))
                        testMessage = "room number";
                }
                startProgramLoop(testMessage);
                break;
            }
        }
    }

    private void startProgramLoop(String message) {
        //parse the message
        switch(parser.getSpeechCommand(message)) {
            case "navigate": SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.navigate) + " " + parser.getDestination());
                nav = new Navigation(getApplicationContext(), parser.getDestination(), "new");
                parser.clear();
                break;
            case "repeat": startListener(getString(R.string.navigate) + " " + parser.getDestination() + " " +getString(R.string.ask_again), REQUEST_LISTENER_END);
                break;
            case "no": startListener(getString(R.string.init_greeting), REQUEST_LISTENER_END);
                break;
            case "room number": startListener(getString(R.string.room_number), REQUEST_LISTENER_END);
                //Toast.makeText(getApplicationContext(), String.valueOf(parser.getDestination() == null), Toast.LENGTH_SHORT).show();
                break;
            case "not exist":startListener(getString(R.string.not_exist), REQUEST_LISTENER_END);
                break;
            case "stop": SpeakActivity say2 = new SpeakActivity(getApplicationContext(), "You chose to stop, goodbye");
                finish();
                break;
            default: startListener(getString(R.string.please_repeat), REQUEST_LISTENER_END);
        }
    }

    public void updateUI() {
        List<Step> stepList = nav.getStepList();

        if(mAdapter == null) {
            mAdapter = new DirectionAdapter(stepList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setStepList(stepList);
            mAdapter.notifyItemChanged(adapterPosition);
        }
    }

    private class DirectionHolder extends RecyclerView.ViewHolder {

        private TextView mStepItemText;


        public DirectionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.step_layout, parent, false));

            mStepItemText = itemView.findViewById(R.id.step_item);
        }

        //could probably just bind a step
        public void bind(Step stepItem) {
            mStepItemText.setText(stepItem.getNode().getName());
            mXLocation.setText(formatter.format(((CustomLocation)stepItem.getNode().getLocation()).getPositionX()));
            mYLocation.setText(formatter.format(((CustomLocation)stepItem.getNode().getLocation()).getPositionY()));
        }
    }

    private class DirectionAdapter extends RecyclerView.Adapter<DirectionHolder> {

        private List<Step> mStepList;

        public DirectionAdapter(List<Step> stepList) {
            mStepList = stepList;
        }

        public DirectionHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new DirectionHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull DirectionHolder holder, int position) {
            if(position != 0)
                holder.bind(mStepList.get(position));
            else
                mCurrentStepDistance.setText(formatter.format(mStepList.get(0).getDistance()) + " Feet");
        }

        @Override
        public int getItemCount() {
            return mStepList.size();
        }

        public void setStepList(List<Step> stepList) {
            mStepList = stepList;
        }



    }
}
