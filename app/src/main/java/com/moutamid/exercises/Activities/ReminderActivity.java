package com.moutamid.exercises.Activities;

import static com.moutamid.exercises.Activities.IntroActivity.simpleDateFormat;
import static com.moutamid.exercises.MainActivity.TimeUser;
import static com.moutamid.exercises.MainActivity.repeattime;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.moutamid.exercises.DataBase.AppDatabase;
import com.moutamid.exercises.DataBase.Reminder;
import com.moutamid.exercises.DataBase.UserDao;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityReminderBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {
    private ActivityReminderBinding binding;
    AppDatabase db;
    UserDao userDao;
    String time, Level;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        TimeUser = sharedPreferences.getString("time", time);
        repeattime = sharedPreferences.getString("days", Level);
        if (TimeUser != null && !TimeUser.equals("")) {
            binding.tvTime.setText(TimeUser);
        } else {
            binding.tvTime.setText(getString(R.string.default_reminder_text));
        }
        if (repeattime != null && !repeattime.equals("")) {
            binding.tvDays.setText(repeattime);
        } else {
            binding.tvDays.setText("");
        }
        Log.d("TimeUser", "" + TimeUser + repeattime);
        binding.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        time = simpleDateFormat.format(calendar.getTime());
                        binding.tvTime.setText(time);
                        showDialog();
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

    }

    public void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);
//        DialogRepeateDaysBinding dialogBinding = DialogRepeateDaysBinding.inflate(getLayoutInflater());
//        builder.setView(dialogBinding.getRoot());
//
//        CheckBox cb_sun = dialogBinding.cbSun;
//        CheckBox cb_mon = dialogBinding.cbMon;
//        CheckBox cb_tue = dialogBinding.cbTue;
//        CheckBox cb_wed = dialogBinding.cbWed;
//        CheckBox cb_thu = dialogBinding.cbThu;
//        CheckBox cb_fri = dialogBinding.cbFri;
//        CheckBox cb_sat = dialogBinding.cbSat;
//
//        final List<CheckBox> checkBoxes = new ArrayList<>();
//        checkBoxes.add(cb_mon);
//        checkBoxes.add(cb_tue);
//        checkBoxes.add(cb_wed);
//        checkBoxes.add(cb_thu);
//        checkBoxes.add(cb_fri);
//        checkBoxes.add(cb_sat);
//        checkBoxes.add(cb_sun);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        builder.setTitle(getResources().getString(R.string.repeat));
//        final List<String> list = new ArrayList<>();
//
//        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                binding.tvDays.setText(Level);
//
//            }
//        });
//
//        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(util.getReminderDaysList().get(i));
        }

        Level = new Gson().toJson(list);
        insertReminder(time, Level, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("time", time);
        editor.putString("days", Level);
        editor.apply();
    }

    public void insertReminder(String time, String repeat, boolean isOn) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insertReminder(new Reminder(time, repeat, isOn));
            }
        }).start();
    }


    public void backpress(View view) {
        onBackPressed();
    }
}