package com.moutamid.exercises.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.faisalkhan.seekbar.bidirectionalseekbar.BiDirectionalSeekBar;
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.moutamid.exercises.Authentication.SignupActivity;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityIntroBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


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
        binding.nameedt.setText(Stash.getString("profile_name"));
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
            Dialog lodingbar = new Dialog(IntroActivity.this);
            lodingbar.setContentView(R.layout.loading);
            Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
            lodingbar.setCancelable(false);
            lodingbar.show();

            User user = new User(gender, name, selectedAge, weight, Type);
            Stash.put("user", user);
            Stash.put("exercise_no", 1);
            Stash.put("exercise_no", 1);
            Stash.put("Streak", 0);
            Stash.put("user_name", name);
            Map<String, Object> updates = new HashMap<>();
            updates.put("gender", gender);
            updates.put("name", name);
            updates.put("age", selectedAge);
            updates.put("weight", weight);
            updates.put("weight_type", Type);
            FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    show_toast("User Profile is created successfully", 1);
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    lodingbar.dismiss();
                    finishAffinity();

                }
            }).addOnFailureListener(new OnFailureListener(




            ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    lodingbar.dismiss();
                    show_toast("Something went wrong. Please try again", 0);
                }
            });

        } else {
            Toast.makeText(this, "Please Enter Your Weight", Toast.LENGTH_SHORT).show();
        }
    }

    public void show_toast(String message, int type) {
        LayoutInflater inflater = getLayoutInflater();

        View layout;
        if (type == 0) {
            layout = inflater.inflate(R.layout.toast_wrong,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
        } else {
            layout = inflater.inflate(R.layout.toast_right,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

        }
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}
