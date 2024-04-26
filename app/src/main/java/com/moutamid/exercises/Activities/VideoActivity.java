package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1; // Example: R.raw.your_video
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(VideoActivity.this, MainActivity.class));
                finishAffinity();
            }
        });
        videoView.start();
    }

    public void backpress(View view) {
        onBackPressed();
    }
}