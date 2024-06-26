package com.moutamid.exercises.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotificationReceiver", "Received notification");
        NotificationHelper.showNotification(context, "Time to Energize!", "Stay active and energized throughout the day!");
    }
}
