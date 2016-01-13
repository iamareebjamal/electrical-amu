package amu.electrical.deptt.messages;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

public class Message implements Serializable {

    private String title;
    private String message;
    private Bundle intent;
    private int timestamp;

    public Message(String title, String message, int timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Message(Intent intent, String title, String message, int timestamp) {
        this.intent = intent.getExtras();
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Bundle getIntent() {
        return intent;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int getTimestamp() {
        return timestamp;
    }


}