package com.moutamid.exercises.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.fxn.stash.Stash;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.Config;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityEditProfileBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         user = (User) Stash.getObject("user", User.class);
        binding.name.setText(Stash.getString("user_name"));
        binding.weightType.setText("Weight ("+user.weight_type+")");
        binding.age.setText(user.age + "");
        binding.weight.setText(user.weight + " ");
        binding.gender.setText(user.gender + "");
        char c = Stash.getString("user_name").charAt(0);
        binding.textView5.setText(c + "");
        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog lodingbar = new Dialog(EditProfileActivity.this);
                lodingbar.setContentView(R.layout.loading);
                Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                lodingbar.setCancelable(false);
                lodingbar.show();


                Map<String, Object> updates = new HashMap<>();
                updates.put("age", binding.age.getText().toString());
                updates.put("weight", binding.weight.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        User userModel = new User(user.gender, user.name, Integer.valueOf(binding.age.getText().toString()), binding.weight.getText().toString(), user.weight_type);
                        Stash.put("user", userModel);

                        show_toast("User Profile is updated successfully", 1);
                        lodingbar.dismiss();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener(


                ) {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lodingbar.dismiss();
                        show_toast("Something went wrong. Please try again", 0);
                    }
                });

            }
        });
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