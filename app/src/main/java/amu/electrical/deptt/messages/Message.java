package amu.electrical.deptt.messages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;

import java.io.Serializable;

public class Message implements Serializable {

    static final long serialVersionUID =2128277653790650387L;

    private String title;
    private String message;
    private String intent;
    private long timestamp;

    public Message(String title, String message, long timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Message(Intent intent, String title, String message, long timestamp) {

        //Intent to String
        Parcel parcel = Parcel.obtain();
        if(intent.getExtras()!=null) {
            intent.getExtras().writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            this.intent = Base64.encodeToString(bytes, 0, bytes.length, 0);
        }

        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Intent getIntent() {
        if(intent==null)
            return null;

        byte[] bytes = Base64.decode(intent, 0);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        Bundle bundle = new Bundle();
        bundle.readFromParcel(parcel);
        return new Intent().putExtras(bundle);
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }


}