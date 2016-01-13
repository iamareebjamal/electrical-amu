package amu.electrical.deptt.utils;

import amu.electrical.deptt.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

public class NotificationUtils {
    public static int ID = 420, NORMAL = ID, POSITIVE = 70, NEGATIVE = 71, MEDIA = 72, ANNOUNCEMENT = 73;
    private int currId = ID;
    private Context ctx;


    public NotificationUtils() {
    }

    public NotificationUtils(Context ctx) {
        this.ctx = ctx;
    }

    public void setId(int id) {
        currId = id;
    }

    public void showNotification(String title, String message, Intent i) {

        PendingIntent resultPendingIntent = PendingIntent.getActivity(ctx, ID, i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Style style = new NotificationCompat.InboxStyle();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        Notification notification = mBuilder
                .setColor(ctx.getResources().getColor(R.color.green_main))
                .setSmallIcon(R.drawable.ic_noti_small)
                .setTicker(title)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setStyle(style)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(currId, notification);
    }
}
