package com.moutamid.exercises.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jawline.exercises.R;
import com.jawline.exercises.Utils.Config;
import com.jawline.exercises.databinding.ActivityRestTimeBinding;

import java.util.concurrent.TimeUnit;

public class RestTimeActivity extends AppCompatActivity {
    private ActivityRestTimeBinding binding;
    int noOfMinutes = (int) (0.33 * 60 * 1000);
    CountDownTimer countDownTimer;
    long lefttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name = getIntent().getStringExtra("name");
        int count = getIntent().getIntExtra("count", 1);
        noOfMinutes = Config.getValue("duration", RestTimeActivity.this);
        int i = count + 1;
        binding.nameEx.setText(name);
        binding.nextNumber.setText("Next " + i + "/6");
        ex_name(name);
        countDownTimer(noOfMinutes);
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                long l = lefttime + noOfMinutes;
                countDownTimer(l);
            }
        });
    }

    public void ex_name(String ex_name) {

        switch (ex_name) {
            case "Ups & Down Nodes":
                binding.animation.setImageResource(R.drawable.up_down);
                break;
            case "Chin Tucks":
                binding.animation.setImageResource(R.drawable.chin_tucks);

                break;
            case "Upward Chewing":
                binding.animation.setImageResource(R.drawable.upward_chewing);
                break;
            case "Extend your Tonge":
                binding.animation.setImageResource(R.drawable.extend_your_tongue);
                break;
            case "Open Mouth Widely":
                binding.animation.setImageResource(R.drawable.open_mouth_widely);
                break;
            case "Massage your face":
                binding.animation.setImageResource(R.drawable.message_your_face);

                break;
            case "Mouthwash Exercise":
                binding.animation.setImageResource(R.drawable.mouth_wash_exercise);
                break;
            case "Tilt Your Head Left & Right":
                binding.animation.setImageResource(R.drawable.tile_your_head_left_righgt);

                break;
            case "Pushing the Tongue Outward":
                binding.animation.setImageResource(R.drawable.pushing_tongue_outward);
                break;
        }

    }

    public void countDownTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                Log.d("lefttieme", lefttime+"");
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(lefttime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lefttime)), TimeUnit.MILLISECONDS.toSeconds(lefttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lefttime)));
                binding.count.setText(hms);
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
        if (Config.getValue("volume", getApplicationContext()) == 0) {
            MediaPlayer mp = MediaPlayer.create(this, voice);
            mp.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    countDownTimer.cancel();
    }
}