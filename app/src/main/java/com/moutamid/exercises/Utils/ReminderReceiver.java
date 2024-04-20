package com.moutamid.exercises.Utils;

import static com.moutamid.exercises.Activities.IntroActivity.simpleDateFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.moutamid.exercises.DataBase.AppDatabase;
import com.moutamid.exercises.DataBase.Reminder;
import com.moutamid.exercises.DataBase.UserDao;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReminderReceiver extends BroadcastReceiver {
    List<Reminder> reminderModels = new ArrayList<>();
    List<String> timeList = new ArrayList<>();
    AppDatabase db;
    UserDao userDao;
    Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Reminder ON Time", Toast.LENGTH_SHORT).show();
        mcontext=context;
        db=AppDatabase.getDatabase(mcontext);
        userDao = db.userDao();
        new ReminderReceiver.DatabaseAsyncTask().execute();

    }
   class DatabaseAsyncTask extends AsyncTask<Void, Void, List<Reminder>> {
        @Override
        protected List<Reminder> doInBackground(Void... voids) {
            // Perform the database operation on the background thread
            return userDao.getReminders();
        }
        @Override
        protected void onPostExecute(List<Reminder> reminders) {
            super.onPostExecute(reminders);
            reminderModels = reminders;
            timeList = getTimeList(reminders);
            Calendar calendarTime = Calendar.getInstance();
            String currentTime = simpleDateFormat.format(calendarTime.getTime());
            String currentday = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendarTime.getTime());
            if (timeList.contains(currentTime)) {
                int i = timeList.indexOf(currentTime);
                Log.d("timeList",timeList.get(i));
                if (reminderModels.get(i).ison) {
                    Log.d("isonList",""+reminderModels.get(i).ison);
                    ArrayList<String> myList = new Gson().fromJson(reminderModels.get(i).repeat, ArrayList.class);
                    if (myList.contains(currentday))
                    {
                        NotificationScheduler.showNotification(mcontext, currentTime);
                    }
                }
            }
        }
    }

    private List<String> getTimeList(List<Reminder> reminders) {
        List<String> timeList = new ArrayList<>();
        for (Reminder reminder : reminders)
        {
            timeList.add(reminder.getTime());
        }
        return timeList;
    }
}
