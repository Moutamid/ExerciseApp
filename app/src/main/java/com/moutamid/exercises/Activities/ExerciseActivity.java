package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.exercises.R;

public class ExerciseActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView play, pause;
    private TextView timerTextView, percentage, next_btn;
    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        next_btn = findViewById(R.id.next_btn);
        videoView = findViewById(R.id.video_view);
        timerTextView = findViewById(R.id.duration);
        percentage = findViewById(R.id.percentage);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1; // Example: R.raw.your_video
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Start the video again when it completes
                videoView.start();
                next_btn.setVisibility(View.VISIBLE);
            }
        });
        videoView.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoView.isPlaying()) {
                    pause.setVisibility(View.VISIBLE);
                    play.setVisibility(View.INVISIBLE);
                    videoView.start();
                    startTime = System.currentTimeMillis() - videoView.getCurrentPosition();
                    timerHandler.postDelayed(timerRunnable, 0);
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);
                    videoView.pause();
                    timerHandler.removeCallbacks(timerRunnable);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void next(View view) {
        startActivity(new Intent(this, RestTimeActivity.class));
    }

    public void back(View view) {
        onBackPressed();
    }
}
