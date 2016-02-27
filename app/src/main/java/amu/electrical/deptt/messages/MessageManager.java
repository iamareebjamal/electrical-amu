package amu.electrical.deptt.messages;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageManager {

    private Context context;
    private MessageDump messageDump;
    private String name = "messages.db";


    public MessageManager(Context context) {
        this.context = context;
        loadMessages();
    }

    public MessageManager(Context context, String name) {
        this.context = context;
        this.name = name;
        loadMessages();
    }

    private void loadMessages() {
        try {
            FileInputStream fis = context.openFileInput(name);
            ObjectInputStream o = new ObjectInputStream(fis);
            messageDump = (MessageDump) o.readObject();
            fis.close();
        } catch (Exception e) {
            //Create if not present
            messageDump = new MessageDump();
            Log.e(MessageDump.TAG, e.getMessage());
        }
    }

    private void saveMessages() {
        try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            ObjectOutputStream o = new ObjectOutputStream(fos);
            o.writeObject(messageDump);
            Log.d(MessageDump.TAG, "Messages saved");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(MessageDump.TAG, e.getMessage());
        }
    }

    public MessageDump getMessageDump() {
        return messageDump;
    }

    public void addMessage(Message m) {
        messageDump.addMessage(m);
    }

    public void clear() {
        messageDump = new MessageDump();
        Log.d(MessageDump.TAG, "Messages cleared");
        saveMessages();
    }

    public void save() {
        saveMessages();
    }

    public void saveMessage(Message m) {
        messageDump.addMessage(m);
        Log.d(MessageDump.TAG, "Message added");
        saveMessages();
    }


}
