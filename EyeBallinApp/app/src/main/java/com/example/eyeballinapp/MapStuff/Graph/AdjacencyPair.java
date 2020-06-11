package com.example.eyeballinapp.MapStuff.Graph;

public class AdjacencyPair {
    private String vertex;
    private double distance;

    public AdjacencyPair(String vertex, double distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public String getVertex() {
        return vertex;
    }

    public double getDistance() {
        return distance;
    }
}
