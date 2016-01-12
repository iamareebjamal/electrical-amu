package amu.electrical.deptt.utils;

import com.parse.ParsePushBroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import org.json.JSONObject;
import org.json.JSONException;
import android.util.Log;
import amu.electrical.deptt.MainActivity;

public class PushReceiver extends ParsePushBroadcastReceiver {
	
	private Intent parseIntent;

	private String TAG = "PushReceiver";
	
	public PushReceiver() {
		super();
	}
	
	@Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        if (intent == null)
            return;
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            
            parseIntent = intent;
            parsePushJson(context, json);
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

	private void parsePushJson(Context context, JSONObject json){
		// TODO: Implement this method
		try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            if (!isBackground) {
                Intent resultIntent = new Intent(context, MainActivity.class);
				showNotificationMessage(context, title, message, resultIntent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
	}

	private NotificationUtils notificationUtils;
	private void showNotificationMessage(Context context, String title, String message, Intent intent)
	{
		// TODO: Implement this method
		notificationUtils = new NotificationUtils(context);
        intent.putExtras(parseIntent.getExtras());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotification(title, message, intent);
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
