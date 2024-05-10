package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;

public class OverviewActivity extends AppCompatActivity {
    Button back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
                Stash.put("videoPath", videoPath);

                startActivity(new Intent(OverviewActivity.this, VideoActivity.class));

            }
        });
    }
}