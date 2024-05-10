package com.moutamid.exercises.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.moutamid.exercises.R;

public class AllExercisesActivity extends AppCompatActivity {
RelativeLayout exercise1,exercise2,exercise3,exercise4,exercise5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);
    exercise1=findViewById(R.id.exercise1);
    exercise2=findViewById(R.id.exercise2);
    exercise3=findViewById(R.id.exercise3);
    exercise4=findViewById(R.id.exercise4);
    exercise5=findViewById(R.id.exercise5);
    exercise1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
            Stash.put("videoPath", videoPath);
            startActivity(new Intent(AllExercisesActivity.this, VideoActivity.class));
        }
    });  exercise2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video2;
            Stash.put("videoPath", videoPath);
            startActivity(new Intent(AllExercisesActivity.this, VideoActivity.class));
        }
    });  exercise3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video3;
            Stash.put("videoPath", videoPath);
            startActivity(new Intent(AllExercisesActivity.this, VideoActivity.class));
        }
    });  exercise4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video4;
            Stash.put("videoPath", videoPath);
            startActivity(new Intent(AllExercisesActivity.this, VideoActivity.class));
        }
    });  exercise5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video5;
            Stash.put("videoPath", videoPath);
            startActivity(new Intent(AllExercisesActivity.this, VideoActivity.class));
        }
    });
    }
}