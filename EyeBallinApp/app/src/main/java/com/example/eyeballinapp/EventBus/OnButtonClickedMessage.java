package com.example.eyeballinapp.EventBus;

public class OnButtonClickedMessage {

    private String message;

    private String direction;

    public OnButtonClickedMessage(String message) {
        this.message = message;
    }

    public OnButtonClickedMessage(String message, String direction) {
        this.message = message;
        this.direction = direction;
    }

    public String getMessage() {
        return message;
    }

    public String getDirection() {
        return direction;
    }
}

