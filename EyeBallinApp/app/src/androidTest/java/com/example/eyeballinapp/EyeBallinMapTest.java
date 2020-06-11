package com.example.eyeballinapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EyeBallinMapTest {
    Context instrumentationContext;
    CustomLocation zeroZero = new CustomLocation(1, 0, 0);

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void CalculateRoute() {
        String[] expectedSteps = {"Outside Hallway 1", "Outside 151, 152", "151"};

        CustomLocation userLocation = new CustomLocation(0, 0, 1);
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("151");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
    }


    //This test mocks the user walking from the south doors to 151
    @Test
    public void navigateFromSouthDoorsto151() {
        String[] expectedSteps = {"Outside Hallway 1", "Outside 151, 152", "151"};
        Route steps;
        CustomLocation userLocation;
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("151");

        userLocation = new CustomLocation(0, 0, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 24, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 1);
        expectedSteps = new String[]{ "Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 30, 1);
        expectedSteps = new String[]{ "Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(18, 30, 1);
        expectedSteps = new String[]{"Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(42, 30, 1);
        expectedSteps = new String[]{"Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 30, 1);
        expectedSteps = new String[]{"151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 24, 1);
        expectedSteps = new String[]{"151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 22, 1); //arrived
        expectedSteps = new String[]{"151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
    }

    //This test mocks the user walking from the south doors to 151 while stepping exactly on each node
    @Test
    public void navigateFrom151to440() {
        String[] expectedSteps = {"Outside 151, 152", "Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        Route steps;
        CustomLocation userLocation;
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("440");

        userLocation = new CustomLocation(56, 22, 1); //151
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 29, 1); // This is within 3 feet of the node
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(28, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 1);
        expectedSteps = new String[]{"Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 1);
        expectedSteps = new String[]{"Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 1);
        expectedSteps = new String[]{"Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 4);
        expectedSteps = new String[]{"Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 4);
        expectedSteps = new String[]{"Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 4);
        expectedSteps = new String[]{"Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(6, 21, 4);
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 13, 4); //arrived
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
        assertEquals(0.0, steps.takeStep().getDistance(), 0.1); //check to see if they have arrived according to the step data.
    }

    //This test mocks the user walking from the south doors to 151 while stepping exactly on each node
    @Test
    public void navigateFrom151to440WithSlightMisses() {
        String[] expectedSteps = {"Outside 151, 152", "Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        Route steps;
        CustomLocation userLocation;
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("440");

        userLocation = new CustomLocation(56, 22, 1); //151
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 29, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(50, 28, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(30, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(28, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(10, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 30, 1);
        expectedSteps = new String[]{"Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 47, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 1);
        expectedSteps = new String[]{"Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(7, 55, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 1);
        expectedSteps = new String[]{"Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 3); // Accidentally hit floor 3.
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 4);
        expectedSteps = new String[]{"Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(8, 55, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 4);
        expectedSteps = new String[]{"Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 34, 4); // They walked slightly diagonally
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(4, 30, 4);
        expectedSteps = new String[]{"Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 30, 4);
        expectedSteps = new String[]{"Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(5, 20, 4);
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(6, 21, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(2, 15, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 13, 4); //arrived
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
        assertEquals(0.0, steps.takeStep().getDistance(), 0.1); //check to see if they have arrived according to the step data.
    }

    //This test mocks the user walking from the south doors to 151 while stepping exactly on each node
    @Test
    public void navigateFrom151to440WithSlightMissesDirections() {
        String[] expectedSteps = {"Outside 151, 152", "Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        Route steps;
        CustomLocation userLocation;
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("440");

        userLocation = new CustomLocation(56, 22, 1); //151
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 29, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(50, 28, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(30, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(28, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(10, 30, 1);
        expectedSteps = new String[]{"Outside Hallway 1", "Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 30, 1);
        expectedSteps = new String[]{"Outside Elevator 1", "Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 47, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 1);
        expectedSteps = new String[]{"Elevator 1", "Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(7, 55, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 1);
        expectedSteps = new String[]{"Elevator 4", "Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 3); // Accidentally hit floor 3.
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 55, 4);
        expectedSteps = new String[]{"Outside Elevator 4", "Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(8, 55, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 55, 4);
        expectedSteps = new String[]{"Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 34, 4); // They walked slightly diagonally
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(4, 30, 4);
        expectedSteps = new String[]{"Outside Hallway 4", "Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(3, 30, 4);
        expectedSteps = new String[]{"Water Fountain 4", "440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(5, 20, 4);
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(6, 21, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(2, 15, 4);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 13, 4); //arrived
        expectedSteps = new String[]{"440"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
        assertEquals(0.0, steps.takeStep().getDistance(), 0.1); //check to see if they have arrived according to the step data.
    }
}