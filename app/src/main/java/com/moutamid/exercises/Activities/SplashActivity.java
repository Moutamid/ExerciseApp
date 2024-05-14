package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.fxn.stash.Stash;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;
import com.moutamid.exercises.Authentication.LoginActivity;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.Config;
import com.moutamid.exercises.Utils.util;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    private int SPLASH_TIME = 1700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.hideStatusBar(this);
        setContentView(R.layout.activity_splash);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    User user = (User) Stash.getObject("user", User.class);
                    if (user == null) {
                        intent = new Intent(SplashActivity.this, IntroActivity.class);

                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                }

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }


}