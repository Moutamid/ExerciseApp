package com.moutamid.exercises.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.Config;

import java.util.concurrent.TimeUnit;

public class RestTimeActivity extends AppCompatActivity {
    int noOfMinutes = 1 * 60 * 1000;
    CountDownTimer countDownTimer;
    long lefttime;
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_time);
        count=findViewById(R.id.count);
       countDownTimer(noOfMinutes);
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