package amu.electrical.deptt.utils;

import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.messages.Message;
import amu.electrical.deptt.messages.MessageDump;
import amu.electrical.deptt.messages.MessageManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.parse.ParsePushBroadcastReceiver;
import org.json.JSONException;
import org.json.JSONObject;

public class PushReceiver extends ParsePushBroadcastReceiver {

    private static boolean SHOW_TESTS = true;

    private MessageManager messageManager;
    private Intent parseIntent;
    private String TAG = "PushReceiver";
    private NotificationUtils notificationUtils;

    public PushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        //super.onPushReceive(context, intent);
        if (intent == null)
            return;

        messageManager = new MessageManager(context);
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("com.parse.Data"));
            Log.d(TAG, "Push Data received : " + json.toString());
            parseIntent = intent;
            if (json.has("alert")) {
                String message = json.getString("alert");
                showNotificationMessage(context, "New Notification", message, new Intent(context, MainActivity.class));
            } else {
                parsePushJson(context, json);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    private void parsePushJson(Context context, JSONObject json) {
        // TODO: Implement this method
        try {
            //Custom JSON
            boolean isBackground = json.getBoolean("is_background");

            //Don't show test notificatons if Release version is out
            try {
                boolean isTest = json.getBoolean("is_test");

                if (isTest) Log.d(TAG, "Test Notification Received.\tSHOW_TEST : " + SHOW_TESTS);
                if (isTest && !SHOW_TESTS)
                    return;

            } catch (JSONException e) {
                Log.e(TAG, "is_test field not found");
            }

            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            if (!isBackground) {
                Intent resultIntent = new Intent(context, MainActivity.class);
                resultIntent.putExtras(parseIntent.getExtras());

                Message receivedMessage = new Message(resultIntent, title, message, System.currentTimeMillis());
                showNotificationMessage(context, receivedMessage.getTitle(), receivedMessage.getMessage(), resultIntent);
                messageManager.saveMessage(receivedMessage);
                Intent messageInserted = new Intent(MessageDump.TAG);
                context.sendBroadcast(messageInserted);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage() + "\nActual Json data:\n" + json.toString());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        // TODO: Implement this method
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotification(title, message, intent);
        Log.d(TAG, "Notification shown : " + message + " : " + title);
    }

    @Override
    protected void onPushDismiss(
            Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(
            Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }


}
