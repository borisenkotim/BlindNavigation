// This class will interact with the voice classes to gather user input and create a navigation plan.
// It will also generate the text that will be spoken to navigate the user to their location
// This class will also create a location listener that will be used to navigate the user
// For testing

package com.example.eyeballinapp.MapStuff.Navigation;

import android.content.Context;
import android.widget.Toast;

import com.example.eyeballinapp.EventBus.OnButtonClickedMessage;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.SpeechStuff.SpeakActivity;
import com.example.eyeballinapp.SpeechStuff.SpeechResult;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Navigation {
    private EyeBallinMap map;
    private Context mContext;
    private CustomLocation userLocation;
    private Route steps;

    private String lastDirection = "N/A";
    private String buttonLocation = "forward";
    private String lastForwardDirection = "N/A";
    private SpeakActivity say;
    private int stepLength = 1;

    public Navigation(Context context, String destination) {
        mContext = context;
        map = new EyeBallinMap(mContext);

        //starting point
        userLocation = new CustomLocation(0, -5, 1);
        map.setDestination(destination);
        map.updateUser(userLocation);
        steps = map.calculateRoute();
        say = new SpeakActivity(mContext, "Take a step in any direction to calibrate the direction sensor");
    }

    public Navigation(Context context, String destination, String test) {
        mContext = context;
        map = new EyeBallinMap(mContext);

        //starting point
        userLocation = SpeechResult.get(mContext).getLastLocation();
        map.setDestination(destination);
        map.updateUser(userLocation);
        steps = map.calculateRoute();
        say = new SpeakActivity(mContext, "");
    }


    public void navigate(String direction) {

        // some code to make sure you can only move really fast in the direction of the next node.
        // some code to make long distances easier to travel in the app.
        double distanceToNextStep = steps.getStep(0).getDistance();
        if (direction.equals(lastForwardDirection) && distanceToNextStep > 7) {
            stepLength = (int) distanceToNextStep / 3;
        } else {
            stepLength = 1;
        }

        switch (direction) {
            //do a post here using eventbus
            case "forward":
                move(userLocation.getPositionX(), userLocation.getPositionY() + stepLength, userLocation.getFloorNum(), "forward");
                buttonLocation = "forward";
                break;
            case "back":
                move(userLocation.getPositionX(), userLocation.getPositionY() - stepLength, userLocation.getFloorNum(), "back");
                buttonLocation = "back";
                break;
            case "left":
                move(userLocation.getPositionX() - stepLength, userLocation.getPositionY(), userLocation.getFloorNum(), "left");
                buttonLocation = "left";
                break;
            case "right":
                move(userLocation.getPositionX() + stepLength, userLocation.getPositionY(), userLocation.getFloorNum(), "right");
                buttonLocation = "right";
                break;
            case "up":
                if (userLocation.getFloorNum() != 4)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() + 1, "elevator");
                break;
            case "down":
                if (userLocation.getFloorNum() != 1)
                    move(userLocation.getPositionX(), userLocation.getPositionY(), userLocation.getFloorNum() - 1, "elevator");
                break;
            default:
        }
        EventBus.getDefault().post(new OnButtonClickedMessage("UPDATE", steps.getGeneralWalkingDirection(buttonLocation)));
    }

    public List<Step> getStepList() {
        return steps.getStepList();
    }

    private void move(double x, double y, int floor, String direction) {
        userLocation.setLocation(x, y, floor); //create new location where the user is now
        map.updateUser(userLocation); //update the user's location in the map
        steps = map.calculateRoute(); // re-calculate the route

        String currentDirection = steps.getGeneralWalkingDirection(direction); //get the general walking direction that is needed to reach the next step

        // store the last direction that was considered forward so we can allow the user to move really fast in the direction of the next step
        if (currentDirection.equals("forward"))
            lastForwardDirection = direction;

        // for mocking elevator stuff
        if (currentDirection.equals("elevator_up")) { //if we need to go up still in the elevator
            say.speak("demo elevator simulation. go up");
            currentDirection = "elevator";
        } else if (currentDirection.equals("elevator_down")) { //if we need to go down still in the elevator
            say.speak("demo elevator simulation. go down");
            currentDirection = "elevator";
        } else if (currentDirection.equals("arrived")) {
            SpeechResult.get(mContext).setLastLocation(userLocation);
            Toast.makeText(mContext, String.valueOf(SpeechResult.get(mContext).getLastLocation().getPositionX()), Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new OnButtonClickedMessage("FINISHED"));
        }

        if (lastDirection != currentDirection) { //if the direction we need to go to has changed, restate the directions.
            say.speak(steps.getVoiceDirections(direction));
            lastDirection = currentDirection; //update the last direction we need to go to.
            EventBus.getDefault().post(new OnButtonClickedMessage("CHANGED", steps.getGeneralWalkingDirection(direction))); //notify the UI that things have been changed
        }
    }
}
