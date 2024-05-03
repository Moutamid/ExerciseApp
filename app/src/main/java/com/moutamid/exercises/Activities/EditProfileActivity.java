package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.fxn.stash.Stash;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.Utils.Config;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityEditProfileBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    String name, age, weight, gender, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = (User) Stash.getObject("user", User.class);
        binding.name.setText(Stash.getString("user_name"));
        binding.age.setText(user.age+"");
        binding.weight.setText(user.weight+" KG");
        binding.gender.setText(user.gender+"");
        char c = Stash.getString("user_name").charAt(0);
        binding.textView5.setText(c + "");
        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


}