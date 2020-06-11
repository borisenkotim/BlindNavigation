package com.example.eyeballinapp.MapStuff.Location;

import android.location.Location;


public class CustomLocation extends Location {

    // The way we have things set up:
    /*
        north is positive y
        south is negative y
        east is positive x
        west is negative x
     */

    private double positionX;
    private double positionY;
    private int floorNum;
    private double floorHeight;

    public CustomLocation(double x, double y, int floor) {
        // This is required
        super("PlaceHolder"); // Lets see
        this.positionX = x;
        this.positionY = y;
        this.floorNum = floor;
        this.floorHeight = calcHeight(floor); //set the floor height in feet to the height of the floor being added
    }

    private double calcHeight(int floor) {
        double h = 17.5; //first floor height is 17.5 feet
        for (int i = 0; i < floor; i++)
            h += 14; //each floor after #1 is 14 feet tall
        return h;
    }

//    public void setPositionY(double y) {
//        this.positionY = y;
//    }
//
//    public void setPositionX(double x) {
//        this.positionX = x;
//    }

    public void setLocation(double x, double y, int floor) {
        this.positionX = x;
        this.positionY = y;
        this.floorNum = floor;
        this.floorHeight = calcHeight(floor); //set the floor height in feet to the height of the floor being added
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    // need to include Z axis in decision for the closest node because otherwise there are multiple nodes in the same location.
    @Override
    public float distanceTo(Location loc) {
        CustomLocation l = (CustomLocation) loc; //cast location to customLocation

        double dX = l.getPositionX() - positionX;
        double dY = l.getPositionY() - positionY;
        double dZ = l.getFloorHeight() - floorHeight;

        double magnitude = Math.sqrt(dX * dX + dY * dY + dZ * dZ);

        return (float) magnitude;
    }

}
