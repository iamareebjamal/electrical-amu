package amu.electrical.deptt.messages;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageDump implements Serializable {

    public static final String TAG = "SparkMessage";
    private ArrayList<Message> messageList = new ArrayList<Message>();

    public MessageDump() {
    }

    public MessageDump(Message message) {
        addMessage(message);
    }

    public MessageDump(Message[] messages) {
        addMessages(messages);
    }

    public void addMessage(Message message) {
        messageList.add(0, message);
    }

    public void addMessages(Message[] messages) {
        for (Message message : messages) {
            messageList.add(0, message);
        }
    }

    public ArrayList<Message> getMessages() {
        return messageList;
    }


}
