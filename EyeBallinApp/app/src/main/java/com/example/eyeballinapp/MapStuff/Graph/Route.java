package com.example.eyeballinapp.MapStuff.Graph;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

import java.util.ArrayList;

public class Route {
    private ArrayList<Step> steps;
    private int currentStep;
    private boolean isArrived;

    public Route(ArrayList<Step> steps) {
        this.steps = steps;
        currentStep = 0;
    }

    public ArrayList<Step> getStepList() {
        return steps;
    }

    public int numberOfSteps() {
        return steps.size();
    }

    //remove a step and update the vector of the step after it to be accurate after the removal.
    public boolean removeStep(int step) {
        if (step <= 0 && step > steps.size())
            return false;
        EBVector oldVector = steps.remove(step).getVector(); //get the vector of the step we removed.
        EBVector nextVector = steps.get(step).getVector(); //get the vector of the step after the one we removed
        EBVector newVector = oldVector.add(nextVector); //add the two vectors;
        steps.get(step).updateVector(newVector); //update the vector of the step

        // https://imgur.com/a/6pPPkBZ - amazing drawing of what we are doing here.

        return true;
    }

    public Step getStep(int stepNumber) {
        if (stepNumber >= 0 && stepNumber < steps.size())
            return steps.get(stepNumber);
        else
            throw new ArrayIndexOutOfBoundsException("index out of bounds: " + stepNumber);
    }

    public Step takeStep() {
        Step s = steps.get(currentStep);
        if (currentStep < steps.size() - 1) //cannot take a step when you are at the last step.
            currentStep++;
        return s;
    }

    public Step nextStep() {
        if (currentStep < steps.size() - 1) //cannot take a step when you are at the last step.
            return steps.get(currentStep + 1);
        return steps.get(currentStep);
    }

    // pass in the direction the user is facing, and get the direction they need to turn as a string.
    // This can be used to tell when new directions are dispatched so we don't bombard the user with text to speech.
    public String getGeneralWalkingDirection(String userIsFacing) {
        Step s = steps.get(currentStep);
        return s.setUserDirection(userIsFacing); //pass the direction the user is facing to the step to see which direction we need to turn.
    }

    // pass in the direction the user is facing, and get the direction they need to turn as a string.
    public String getVoiceDirections(String userIsFacing) {
        Step s = steps.get(currentStep);
        String direction = s.setUserDirection(userIsFacing); //pass the direction the user is facing to the step to see which direction we need to turn.
        double d = s.getDistance();
        int integerDistance = (int) Math.round(s.getDistance()); //round the distance and cast to an integer
        String spokenDirections;

        // either need to get the direction and build a voice string from it here, or change the return value of direction in the other class to return the voice string.
        // It probably makes more sense to use a case statement here just in case we need direction in another locaation.
        // Since we are using easy to define strings, a switch statement is likely the easiest approach.

        // We want this to return the direction the user needs to turn, and then tell them how far to walk in that direction.

        // Should say things like "continue straight for 10 feet"

        // check to see if this is an elevator direction
        if (direction.contains("elevator")) {
            // get some information that we need to find the floor numbers of where we are and where we need to go
            String elevatorName = getStep(currentStep).getNode().getName(); // get the name of the elevator
            CustomLocation l = (CustomLocation) s.getNode().getLocation();

            // parse out the floor numbers
            String e = elevatorName.substring(elevatorName.length() - 1);
            int destinationFloor = Integer.parseInt(e); // Number that
            int currentFloor = l.getFloorNum();

            // create directions based on which way we need to go.
            spokenDirections = "You are at the elevators, there are elevator doors to one elevator on your right," +
                    " and one to your left. The buttons to call the elevator are in front of you. Call an elevator, and go to floor " + destinationFloor;

        } else { // walking directions
            // Add the direction to go at the start of the string that will be spoken
            switch (direction) {
                case "forward":
                    spokenDirections = "Walk forward ";
                    break;
                case "slight_left":
                    spokenDirections = "take a slight left and walk ";
                    break;
                case "left":
                    spokenDirections = "turn left and walk ";
                    break;
                case "120degrees_left":
                    spokenDirections = "turn 120 degrees to the left and walk ";
                    break;
                case "backward":
                    spokenDirections = "You are facing the wrong way! Turn around and walk ";
                    break;
                case "slight_right":
                    spokenDirections = "take a slight right and walk ";
                    break;
                case "right":
                    spokenDirections = "turn right and walk ";
                    break;
                case "120degrees_right":
                    spokenDirections = "turn 120 degrees to the right and walk ";
                    break;
                case "arrived":
                    return "You have arrived. The door is in front of you.";
                default:
                    throw new IndexOutOfBoundsException("unexpected direction output");
            }

            // Add the distance that needs to be walked in feet after the text string
            spokenDirections += integerDistance + " feet.";
        }

        return spokenDirections;

    }
}
