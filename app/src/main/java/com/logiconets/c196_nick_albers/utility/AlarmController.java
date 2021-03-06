package com.logiconets.c196_nick_albers.utility;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.logiconets.c196_nick_albers.utility.Constants.ALARM_NOTIFICATION_ID;
import static com.logiconets.c196_nick_albers.utility.Constants.ALARM_TEXT_ID;
import static com.logiconets.c196_nick_albers.utility.Constants.ALARM_TITLE_ID;

public class AlarmController {
    private Intent intent;
    private PendingIntent notifyPendingIntent;
    private Boolean isArmed;
    private AlarmManager alarmManager;
    private String alarmTitle;
    private Date alarmDate;
    private NotificationManager mNotificationManager;
    private SimpleDateFormat sdf;
    private Context context;
    private int notificationId;

    public AlarmController(String alarmTitle, Date alarmDate, Context context) {
        this.isArmed = false;
        this.alarmTitle = alarmTitle;
        this.alarmDate = alarmDate;
        this.context = context;
        notificationId = new Random().nextInt(10000);

        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        intent = new Intent(context, AlarmReceiver.class)
                .putExtra(ALARM_TITLE_ID,alarmTitle)
                .putExtra(ALARM_TEXT_ID,alarmTitle + " is coming up soon!")
                .putExtra(ALARM_NOTIFICATION_ID, notificationId);
        notifyPendingIntent = PendingIntent.getBroadcast(context, notificationId,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        isArmed = (PendingIntent.getBroadcast(context, notificationId, intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        sdf = new SimpleDateFormat("MM/dd/yyyy");

    }

    public PendingIntent getNotifyPendingIntent() {
        return notifyPendingIntent;
    }

    public void setNotifyPendingIntent(PendingIntent notifyPendingIntent) {
        this.notifyPendingIntent = notifyPendingIntent;
    }

    public Boolean getArmed() {
        return isArmed;
    }

    public void setArmed(Boolean armed) {
        isArmed = armed;
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAlarm(){
        String toastMessage;
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR);
        today.clear(Calendar.HOUR_OF_DAY);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        Date dateOnly = new Date(today.getTimeInMillis() - today.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000);

        toastMessage = "Start Date before current Date";
        if(alarmDate.after(dateOnly) || alarmDate.equals(dateOnly)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmDate.getTime(), notifyPendingIntent);
                toastMessage = alarmTitle + " Notification is On!\nSet to " + alarmDate.toString();
            }
        }
        Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(){
        String toastMessage;
        if(alarmManager != null){
            alarmManager.cancel(notifyPendingIntent);
        }
        mNotificationManager.cancelAll();
        toastMessage = alarmTitle + " Notification is Off!";
        Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show();

    }

}
