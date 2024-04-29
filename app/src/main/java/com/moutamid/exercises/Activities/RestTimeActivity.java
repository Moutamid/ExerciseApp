package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.exercises.R;

import java.util.concurrent.TimeUnit;

public class RestTimeActivity extends AppCompatActivity {
    int noOfMinutes = 1 * 60 * 1000;
    CountDownTimer countDownTimer;
    long lefttime;
    TextView count, name_ex, sets, reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_time);
        count = findViewById(R.id.count);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        name_ex = findViewById(R.id.name_ex);
        countDownTimer(noOfMinutes);
        int currentExercise = Stash.getInt("exercise_no", 1);
        int currentSet = Stash.getInt("exercise_sets", 1);
        switch (currentSet) {
            case 1:
                sets.setText("Set One");
                reps.setText("12 Reps");
                break;
            case 2:
                sets.setText("Set Two");
                reps.setText("11 Reps");
                break;
            case 3:
                sets.setText("Set Three");
                reps.setText("10 Reps");
                break;
        }
        switch (currentExercise) {
            case 1:

                name_ex.setText("LATERAL RAISES");
                break;
            case 2:
                name_ex.setText("TRICEPS EXTENSIONS");
                break;
            case 3:

                name_ex.setText("LEG EXTENSIONS");
                break;
            case 4:
                name_ex.setText("LEG CURLS");
                break;
            case 5:
                name_ex.setText("STANDING KICKBACKS");
                break;
        }
    }
    public void countDownTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                Log.d("lefttieme", lefttime+"");
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(lefttime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lefttime)), TimeUnit.MILLISECONDS.toSeconds(lefttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lefttime)));
                count.setText(hms);
                 if (lefttime<4000 && lefttime> 3000) {
                    play_voice(R.raw.three);
                }else if (lefttime<3000 && lefttime> 2000) {
                    play_voice(R.raw.two);
                }else if (lefttime<2000 && lefttime> 1000) {
                    play_voice(R.raw.one);
                }}

            @Override
            public void onFinish() {
                startActivity(new Intent(RestTimeActivity.this, ExerciseActivity.class));
                finish();
            }
        }.start();

    }

    public void play_voice(int voice) {
            MediaPlayer mp = MediaPlayer.create(this, voice);
            mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    countDownTimer.cancel();
    }
}