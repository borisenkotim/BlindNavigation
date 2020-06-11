package com.example.eyeballinapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StepTest {
    Context instrumentationContext;
    CustomLocation zeroZero = new CustomLocation(1, 0, 0);

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void UpCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(0, -1, 1);
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("backward", direction);
    }

    @Test
    public void RightCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, 0, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("backward", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("right", direction);
    }

    @Test
    public void LeftCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(-3, 0, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("right", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("backward", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("left", direction);
    }

    @Test
    public void DownCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(0, 3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("backward", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("right", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("forward", direction);
    }

    @Test
    public void UpRightDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(-3, -3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("120degrees_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("120degrees_left", direction);
    }

    @Test
    public void UpLeftDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, -3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("120degrees_right", direction);
    }

    @Test
    public void DownRightDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(-3, 3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("120degrees_right", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("slight_left", direction);
    }

    @Test
    public void DownLeftDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, 3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("120degrees_right", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("slight_right", direction);
    }

    @Test
    public void ElevatorUpTesting() {
        CustomLocation userLocation = new CustomLocation(9, 55, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("Elevator 3");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        // we do not need to do elevator testing for diagonal since it will only trigger if they are EXACTLY on the point of the elevator

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("elevator_up", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("elevator_up", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("elevator_up", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("elevator_up", direction);
    }

    @Test
    public void ElevatorDownTesting() {
        CustomLocation userLocation = new CustomLocation(9, 55, 3); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("Elevator 1");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        // we do not need to do elevator testing for diagonal since it will only trigger if they are EXACTLY on the point of the elevator

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("elevator_down", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("elevator_down", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("elevator_down", direction);

        direction = steps.getStep(0).setUserDirection("back");
        assertEquals("elevator_down", direction);
    }
}