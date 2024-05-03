package com.moutamid.exercises.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
// NotificationScheduler.java
public class NotificationScheduler {

    // Method to schedule notifications
    public static void scheduleNotifications(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Monday
        scheduleNotification(alarmManager, context, Calendar.MONDAY, 8); // 8 AM
        scheduleNotification(alarmManager, context, Calendar.MONDAY, 10); // 10 AM
        scheduleNotification(alarmManager, context, Calendar.MONDAY, 13); // 1 PM
        scheduleNotification(alarmManager, context, Calendar.MONDAY, 15); // 3 PM

        // Thursday
        scheduleNotification(alarmManager, context, Calendar.THURSDAY, 8); // 8 AM
        scheduleNotification(alarmManager, context, Calendar.THURSDAY, 10); // 10 AM
        scheduleNotification(alarmManager, context, Calendar.THURSDAY, 13); // 1 PM
        scheduleNotification(alarmManager, context, Calendar.THURSDAY, 15); // 3 PM

        // Friday
        scheduleNotification(alarmManager, context, Calendar.FRIDAY, 8); // 8 AM
        scheduleNotification(alarmManager, context, Calendar.FRIDAY, 10); // 10 AM
        scheduleNotification(alarmManager, context, Calendar.FRIDAY, 13); // 1 PM
        scheduleNotification(alarmManager, context, Calendar.FRIDAY, 15); // 3 PM
    }

    // Method to schedule a single notification
    private static void scheduleNotification(AlarmManager alarmManager, Context context, int dayOfWeek, int hourOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, dayOfWeek * 100 + hourOfDay, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}


