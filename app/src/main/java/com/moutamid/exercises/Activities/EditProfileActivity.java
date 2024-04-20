package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moutamid.exercises.Utils.Config;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityEditProfileBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    private FirebaseFirestore db;
    String name, email, id;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        id = Config.getStringValue("id", getApplicationContext());
        name = Config.getStringValue("user_name", getApplicationContext());
             binding.name.setText(name);


        char c = name.charAt(0);
        binding.textView5.setText(c + "");
        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(EditProfileActivity.this, gso);
                googleSignInClient.signOut();
                startActivity(new Intent(EditProfileActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
        getUserInfo();
//        binding.update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.pb.setVisibility(View.VISIBLE);
//
//                addDataToFirestore(id, binding.age.getText().toString(), binding.weight.getText().toString(), binding.gender.getText().toString());
//            }
//        });
    }

//    private void addDataToFirestore(String id, String age, String weight, String gender) {
//        CollectionReference dbCourses = db.collection("UsersInfo");
//        DocumentReference document = dbCourses.document(id);
//        Log.d("path", document.getPath());
//        Map<String, Object> data = new HashMap<>();
//        data.put("age", age);
//        data.put("weight", weight);
//        data.put("gender", gender);
//
//        document.set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                binding.pb.setVisibility(View.GONE);
//                Toast.makeText(EditProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
//                onBackPressed();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(EditProfileActivity.this, "Something went wrong" + e.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getUserInfo() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("UsersInfo").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                name = documentSnapshot.get("name").toString();
                email = documentSnapshot.get("email").toString();
                String photoUrl = documentSnapshot.get("photo_url").toString();
                Log.d("photo", photoUrl);
                if (photoUrl.equals("null")) {
                    binding.profileImage.setVisibility(View.GONE);
                    binding.textView5.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(EditProfileActivity.this).load(photoUrl).into(binding.profileImage);
                    binding.profileImage.setVisibility(View.VISIBLE);
                    binding.textView5.setVisibility(View.GONE);


                }
                binding.email.setText(email);
                binding.pb.setVisibility(View.GONE);
            }
        });
    }


}