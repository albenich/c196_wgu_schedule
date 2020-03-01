package com.logiconets.c196_nick_albers.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.logiconets.c196_nick_albers.CourseEditorActivity;
import com.logiconets.c196_nick_albers.CoursesActivity;
import com.logiconets.c196_nick_albers.MainActivity;
import com.logiconets.c196_nick_albers.R;

import static com.logiconets.c196_nick_albers.utility.Constants.ALARM_TEXT_ID;
import static com.logiconets.c196_nick_albers.utility.Constants.ALARM_TITLE_ID;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 1337;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static String contentTitle;
    private static String contentText;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.i("AlarmReceiver", "Title = " + contentTitle);
        Log.i("AlarmReceiver", "Text = " + contentText);
        deliverNotification(context);
    }

    private void deliverNotification(Context context){
        Intent contentIntent = new Intent(context, CoursesActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID,
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logic_one_logo)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void createNotificationChannel(Context context) {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Course notice",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies the student that the Course starts in a week");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static String getContentTitle() {
        return contentTitle;
    }

    public static void setContentTitle(String contentTitle) {
        AlarmReceiver.contentTitle = contentTitle;
    }

    public static String getContentText() {
        return contentText;
    }

    public static void setContentText(String contentText) {
        AlarmReceiver.contentText = contentText;
    }
}
