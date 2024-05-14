package com.moutamid.exercises.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.exercises.Adapter.UserAdapter;
import com.moutamid.exercises.Authentication.SignupActivity;
import com.moutamid.exercises.Model.UserInfo;
import com.moutamid.exercises.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuddiesActivity extends AppCompatActivity {

    public static  UserAdapter userAdapter;
    public static  DatabaseReference databaseReference;
    public static  DatabaseReference databaseReferenceBuddies;
    public static  List<UserInfo> userList;
    public static  List<UserInfo> buddiesList;
    public static  Dialog lodingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddies);

        userList = new ArrayList<>();
        buddiesList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
//                progressBar.setVisibility(View.VISIBLE);
        lodingbar = new Dialog(BuddiesActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        EditText editTextSearch = findViewById(R.id.searchEditText);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        databaseReference =  FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users");
        databaseReferenceBuddies =  FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Buddies");
        fetchUsersFromFirebase();
    }

    public static void fetchUsersFromFirebase() {
        databaseReferenceBuddies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                buddiesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInfo user_budy = snapshot.getValue(UserInfo.class);
                    if (user_budy != null&& !user_budy.id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        buddiesList.add(user_budy);
                        Log.d("buddiesList", user_budy.id+"data");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                userList.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    UserInfo user = snapshot.getValue(UserInfo.class);
                                    if (!user_budy.id.equals(user.id)&&user != null&& !user.id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        userList.add(user);
                                    }
                                }
                                userAdapter.setUserList(userList);
                                lodingbar.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle possible errors.
                                lodingbar.dismiss();
                            }
                        });
                    }


                    return;
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserInfo user = snapshot.getValue(UserInfo.class);
                            if (user != null&& !user.id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                userList.add(user);
                            }
                        }
                        userAdapter.setUserList(userList);
                        lodingbar.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors.
                        lodingbar.dismiss();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
                lodingbar.dismiss();

            }
        });

    }
}
