package com.example.eyeballinapp.MapStuff.Graph;

public class EBVector {
    private double y;
    private double x;
    private double magnitude;

    public static double ThirtyDegrees = 1.732 / 2; // sqrt(3)/2
    public static double NegThirtyDegrees = 1.732 / -2; // -sqrt(3)/2
    public static double piOver12y = 0.2588190451; //15 degrees on the unit circle
    public static double piOver12x = 0.96592582628; //15 degrees on the unit circle
    public static double fivePiOver12y = 0.96592582628; //75 degreees on the unit circle
    public static double fivePiOver12x = 0.2588190451; //75 degrees on the unit circle
    public static double sevenPiOver12y = 0.96592582628; //105 degrees on the unit circle
    public static double sevenPiOver12x = -0.2588190451; //105 degrees on the unit circle
    public static double elevenPiOver12y = 0.2588190451; //165 degreees on the unit circle
    public static double elevenPiOver12x = -0.96592582628; //165 degrees on the unit circle
    public static double thirteenPiOver12y = -0.2588190451; //75 degreees on the unit circle
    public static double thirteenPiOver12x = -0.96592582628; //75 degrees on the unit circle
    public static double seventeenPiOver12y = -0.96592582628; //105 degrees on the unit circle
    public static double seventeenPiOver12x = -0.2588190451; //105 degrees on the unit circle
    public static double nineteenPiOver12y = -0.96592582628; //165 degreees on the unit circle
    public static double nineteenPiOver12x = 0.2588190451; //165 degrees on the unit circle
    public static double twentyThreePiOver12y = -0.2588190451; //165 degreees on the unit circle
    public static double twentyThreePiOver12x = 0.96592582628; //165 degrees on the unit circle

    public EBVector(double xDistance, double yDistance) {
        x = xDistance;
        y = yDistance;

        magnitude = Math.sqrt(x * x + y * y); // square both distances and take the square root of their sum to get the total magnitude.
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public EBVector getUnitVector() {
        if (magnitude == 0)
            return new EBVector(0, 0);

        double newX = x / magnitude;
        double newY = y / magnitude;

        return new EBVector(newX, newY);
    }

    // Behaves like + operator, does not update internal values, and instead returns a new EBVector that has the addition.
    public EBVector add(EBVector v) {
        double newX = x + v.getX();
        double newY = y + v.getY();

        return new EBVector(newX, newY);
    }

    // Behaves like - operator, does not update internal values, and instead returns a new EBVector.
    public EBVector subtract(EBVector v) {
        double newX = x - v.getX();
        double newY = y - v.getY();

        return new EBVector(newX, newY);
    }

    // Behaves like . operator, does not update internal values, and instead returns a new EBVector.
    public double dot(EBVector v) {
        double newX = x * v.getX();
        double newY = y * v.getY();

        return newX + newY;
    }

    // other vector addition functions and shit
}
