package com.moutamid.exercises.Activities;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.faisalkhan.seekbar.bidirectionalseekbar.BiDirectionalSeekBar;
import com.google.gson.Gson;
import com.moutamid.exercises.DataBase.AppDatabase;
import com.moutamid.exercises.DataBase.Reminder;
import com.moutamid.exercises.DataBase.UserDao;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityIntroBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class IntroActivity extends AppCompatActivity {
    private Handler handler;
    String time = null;
    String gender = "Male", Type = "KG";
    private String name, weight;
    int selectedAge;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
    private ActivityIntroBinding binding;
    AppDatabase db;
    UserDao userDao;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppDatabase.getDatabase(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        binding.tvTime.setText(getString(R.string.default_reminder_text));
        userDao = db.userDao();
//        String name1 = getIntent().getStringExtra("name");
//        if (name1.length() > 1) {
//            binding.nameedt.setText(name1);
//        }
        binding.layout1.setVisibility(View.VISIBLE);
        binding.layout2.setVisibility(View.GONE);
        binding.layout3.setVisibility(View.GONE);
        binding.layout4.setVisibility(View.GONE);
        binding.layout5.setVisibility(View.GONE);
        binding.layout6.setVisibility(View.GONE);
        binding.kg.setBackgroundResource(R.drawable.button);
        binding.Lb.setBackgroundResource(R.drawable.outline_white_box);
        binding.kgText.setTextColor(getColor(R.color.white));
        binding.textLb.setTextColor(getColor(R.color.black));
        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finishAffinity();
            }
        });

        binding.kg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Type = "KG";
                binding.kg.setBackgroundResource(R.drawable.button);
                binding.Lb.setBackgroundResource(R.drawable.outline_white_box);
                binding.kgText.setTextColor(getColor(R.color.white));
                binding.textLb.setTextColor(getColor(R.color.black));
            }
        });
        binding.Lb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Type = "LB";
                binding.Lb.setBackgroundResource(R.drawable.button);
                binding.kg.setBackgroundResource(R.drawable.outline_white_box);
                binding.kgText.setTextColor(getColor(R.color.black));
                binding.textLb.setTextColor(getColor(R.color.white));
            }
        });
        binding.male.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                gender = "Male";
                binding.male.setBackgroundResource(R.drawable.outline_box);
                binding.female.setBackgroundResource(R.drawable.outline_white_box);
                binding.other.setBackgroundResource(R.drawable.outline_white_box);
            }
        });

        binding.female.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                gender = "Female";
                binding.female.setBackgroundResource(R.drawable.outline_box);
                binding.male.setBackgroundResource(R.drawable.outline_white_box);
                binding.other.setBackgroundResource(R.drawable.outline_white_box);
            }
        });

        binding.other.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                gender = "Other";
                binding.other.setBackgroundResource(R.drawable.outline_box);
                binding.female.setBackgroundResource(R.drawable.outline_white_box);
                binding.male.setBackgroundResource(R.drawable.outline_white_box);
            }
        });

        binding.ageuser.setOnSeekBarChangeListener(new BiDirectionalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(BiDirectionalSeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(BiDirectionalSeekBar seekBar, int progress, boolean fromUser) {
                selectedAge = seekBar.getProgress();

            }

            @Override
            public void onStopTrackingTouch(BiDirectionalSeekBar seekBar) {
            }
        });

        binding.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(IntroActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        String Level = new Gson().toJson(list);
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

    public void lay1(View view) {
        binding.layout2.setVisibility(View.VISIBLE);
        binding.layout1.setVisibility(View.GONE);
        binding.layout3.setVisibility(View.GONE);
        binding.layout4.setVisibility(View.GONE);
        binding.layout5.setVisibility(View.GONE);
        binding.layout6.setVisibility(View.GONE);
    }

    public void lay2(View view) {
        if (!binding.nameedt.getText().toString().isEmpty()) {
            name = binding.nameedt.getText().toString();
            binding.layout3.setVisibility(View.VISIBLE);
            binding.layout1.setVisibility(View.GONE);
            binding.layout2.setVisibility(View.GONE);
            binding.layout4.setVisibility(View.GONE);
            binding.layout5.setVisibility(View.GONE);
            binding.layout6.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
        }
    }

    public void lay3(View view) {
        if (selectedAge != 0) {
            binding.layout4.setVisibility(View.VISIBLE);
            binding.layout1.setVisibility(View.GONE);
            binding.layout2.setVisibility(View.GONE);
            binding.layout3.setVisibility(View.GONE);
            binding.layout5.setVisibility(View.GONE);
            binding.layout6.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Please Select Your Age", Toast.LENGTH_SHORT).show();
        }
    }

    public void lay4(View view) {
        if (!binding.weightedt.getText().toString().isEmpty()) {
            weight = binding.weightedt.getText().toString() + Type;
            binding.layout5.setVisibility(View.VISIBLE);
            binding.layout1.setVisibility(View.GONE);
            binding.layout2.setVisibility(View.GONE);
            binding.layout3.setVisibility(View.GONE);
            binding.layout4.setVisibility(View.GONE);
            binding.layout6.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Please Enter Your Weight", Toast.LENGTH_SHORT).show();
        }
    }

    public void lay5(View view) {
//        if (binding.tvTime.getText().toString().equals(getString(R.string.default_reminder_text))) {
//            Toast.makeText(this, "Select Time Before", Toast.LENGTH_SHORT).show();
//        } else {
//            new bgThread().start();

//            binding.layout6.setVisibility(View.VISIBLE);
//            binding.layout1.setVisibility(View.GONE);
//            binding.layout2.setVisibility(View.GONE);
//            binding.layout3.setVisibility(View.GONE);
//            binding.layout4.setVisibility(View.GONE);
//            binding.layout5.setVisibility(View.GONE);
//            int animationDuration = 2500; // 2500ms = 2,5s
//            binding.progress.setProgressWithAnimation(100, animationDuration); // Default duration = 1500ms
//            handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    binding.tvTime.setText("");
        // Perform the transition to the new activity
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(util.getReminderDaysList().get(i));

        }
        String Level = new Gson().toJson(list);
        insertReminder(time, Level, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("time", time);
        editor.putString("days", Level);
        editor.apply();
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class bgThread extends Thread {
        @Override
        public void run() {
            super.run();
//            userDao.insert(new User(gender, name, selectedAge, weight));

        }
    }

}
