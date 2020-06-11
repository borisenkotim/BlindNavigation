package com.example.eyeballinapp.MapStuff.Graph;

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

import java.util.HashMap;

public class MapNode {
    private String name;
    private Location location; // GPS location of the node
    private int id;
    private HashMap<String, Double> adjacency; //one slice of the adjacency matrix.
    private boolean isRequired; // Some nodes cannot be skipped diagonally because they avoid walls.

    public MapNode(String nodeName, HashMap<String, Double> adjacentNodes, CustomLocation loc, int id) {
        this.name = nodeName;
        this.adjacency = adjacentNodes;
        this.location = loc;
        this.id = id;
    }

    public MapNode(String nodeName, CustomLocation loc) {
        name = nodeName;
        adjacency = new HashMap<String, Double>();
        isRequired = false;

        // COMMENT OUT LOCATION IF YOU WANT TO RUN UNIT TESTS IN ANDROID STUDIO
        location = loc; //Cannot run android specific things in tests on laptop!!!
        // COMMENT OUT LOCATION IF YOU WANT TO RUN UNIT TESTS IN ANDROID STUDIO
    }

    public MapNode(String nodeName, CustomLocation loc, boolean req) {
        name = nodeName;
        adjacency = new HashMap<String, Double>();
        this.isRequired = req;

        // COMMENT OUT LOCATION IF YOU WANT TO RUN UNIT TESTS IN ANDROID STUDIO
        location = loc; //Cannot run android specific things in tests on laptop!!!
        // COMMENT OUT LOCATION IF YOU WANT TO RUN UNIT TESTS IN ANDROID STUDIO
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public HashMap<String, Double> getAdjacency() {
        return adjacency;
    }

    public double isAdjacent(String nodeName) {
        if (adjacency.containsKey(nodeName)) {
            return adjacency.get(nodeName); //return the distance if the node is adjacent to this one.
        }
        return -1; //return -1 if the node is not adjacent.
    }

    //add an edge to this node. boolean return informs if the operation was successful
    public boolean addEdge(String destination, double distance) {
        if (!adjacency.containsKey(destination)) {
            adjacency.put(destination, distance);
            return true;
        } else {
            return false;
        }
    }

    //remove an edge from this node. boolean return informs if the operation was successful
    public boolean removeEdge(String destination) {
        if (adjacency.containsKey(destination)) {
            adjacency.remove(destination);
            return true;
        } else {
            return false;
        }
    }
}

