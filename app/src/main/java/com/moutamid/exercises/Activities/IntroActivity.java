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
import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.moutamid.exercises.DataBase.User;
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

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        binding.tvTime.setText(getString(R.string.default_reminder_text));
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
            weight = binding.weightedt.getText().toString();
            binding.layout5.setVisibility(View.GONE);
            binding.layout1.setVisibility(View.GONE);
            binding.layout2.setVisibility(View.GONE);
            binding.layout3.setVisibility(View.GONE);
            binding.layout4.setVisibility(View.VISIBLE);
            binding.layout6.setVisibility(View.GONE);

            User user = new User(gender, name, selectedAge, weight);
            Stash.put("user", user);
            Stash.put("exercise_no", 1);
            Stash.put("exercise_no", 1);
            Stash.put("user_name", name);
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please Enter Your Weight", Toast.LENGTH_SHORT).show();
        }
    }



}
