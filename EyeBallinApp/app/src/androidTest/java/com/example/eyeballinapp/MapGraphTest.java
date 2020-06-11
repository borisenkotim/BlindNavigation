package com.example.eyeballinapp;

import androidx.test.runner.AndroidJUnit4;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Graph.Route;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MapGraphTest {
    CustomLocation zeroZero = new CustomLocation(1,0,0);

    @Test
    public void getSize() {
        MapGraph map = new MapGraph();
    }

    @Test
    public void addNode() {
        MapGraph map = new MapGraph();

        assertEquals(0, map.getSize());

        MapNode testNode = new MapNode("test1", zeroZero);
        map.addNode(testNode);
        assertEquals(1, map.getSize());

        testNode = new MapNode("test2", zeroZero);
        map.addNode(testNode);
        testNode = new MapNode("test3", zeroZero);
        map.addNode(testNode);
        testNode = new MapNode("test3", zeroZero);
        map.addNode(testNode);
        assertEquals(3, map.getSize());
    }

    @Test
    public void addNode1() {

    }

    @Test
    public void addEdge() {
        MapGraph map = new MapGraph();
        MapNode testNode = new MapNode("test1", zeroZero);
        map.addNode(testNode);
        testNode = new MapNode("test2", zeroZero);
        map.addNode(testNode);
        map.addEdge("test1", "test2", 9);

        assertTrue(map.getNode("test1").getAdjacency().containsKey("test2"));
        assertTrue(map.getNode("test2").getAdjacency().containsKey("test1"));
    }

    @Test
    public void nearestNodeName() {
    }

    @Test
    public void nearestNode() {

    }

    @Test
    public void navigateFrom() {
        MapGraph map = new MapGraph();

        int[][] adjacencyMatrix = { //Floor 1
                {0, 72, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
                {72, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //1
                {0, 25, 0, 30, 11, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //2
                {0, 0, 30, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 3
                {0, 0, 11, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //4
                {0, 0, 9, 0, 0, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
                {0, 0, 0, 0, 0, 19, 0, 8, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //6
                {0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //7
                {0, 0, 0, 0, 0, 0, 28, 0, 0, 8, 8, 23, 0, 0, 0, 0, 0, 0, 0, 0}, //8
                {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //9
                {0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
                {0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 0, 0, 8, 8, 35, 0, 0, 0, 0, 0}, //11
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0}, //12
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0}, //13
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 8, 8, 38, 0, 0}, //14
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0}, //15
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0}, //16
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 38, 0, 0, 0, 8, 8}, //17
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0}, //18
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0} // 19
        };

        // expected path from 0 to 19 is:
        String[] expected = {"test1", "test2", "test5", "test6", "test8", "test11", "test14", "test17", "test19"};

        MapNode testNode; // Temporary node object to add to

        for (int j = 0; j < adjacencyMatrix.length; j++) { // Go through each vertex in the adjacency matrix
            testNode = new MapNode("test" + j, zeroZero);
            map.addNode(testNode);
        }

        assertEquals(adjacencyMatrix.length, map.getSize());

        for (int j = 0; j < adjacencyMatrix.length; j++) { // Go through each vertex in the adjacency matrix
            for (int k = 0; k < adjacencyMatrix[j].length; k++) //Go through the nodes adjacency list
                if (adjacencyMatrix[j][k] != 0) //only add the nodes that it is actually connected to
                    map.addEdge("test" + j, "test" + k, adjacencyMatrix[j][k]); // add the node adjacency tuple to the hashmap
        }

        Route steps = map.navigateFrom("test0", "test19");

        assertEquals(expected.length, steps.numberOfSteps());

        for (int j = 0; j < expected.length; j++) { // Go through each vertex in the adjacency matrix
            assertEquals(expected[j], steps.takeStep().getNode().getName());
        }
    }
}