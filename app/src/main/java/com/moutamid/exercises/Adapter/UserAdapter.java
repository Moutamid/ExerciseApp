package com.moutamid.exercises.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.exercises.Activities.BuddiesActivity;
import com.moutamid.exercises.Activities.IntroActivity;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.Model.UserInfo;
import com.moutamid.exercises.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<UserInfo> userList;
    private List<UserInfo> userListFull;

    public UserAdapter(List<UserInfo> userList) {
        this.userList = userList;
        this.userListFull = new ArrayList<>(userList);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserInfo currentUser = userList.get(position);
        holder.textViewName.setText(currentUser.getName());
        holder.textViewEmail.setText(currentUser.getEmail());
        holder.add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("name", currentUser.name);
                updates.put("id", currentUser.id);
                updates.put("token", currentUser.token);
                FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Buddies").child(currentUser.getId()).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        BuddiesActivity.fetchUsersFromFirebase();
//                        show_toast("User Profile is created successfully", 1);
//                        startActivity(new Intent(IntroActivity.this, MainActivity.class));
//                        lodingbar.dismiss();
//                        finishAffinity();

                    }
                }).addOnFailureListener(new OnFailureListener(




                ) {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        lodingbar.dismiss();
//                        show_toast("Something went wrong. Please try again", 0);
                    }
                });

            }
        });
    }
    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
        this.userListFull = new ArrayList<>(userList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void filter(String text) {
        userList.clear();
        if (text.isEmpty()) {
            userList.addAll(userListFull);
        } else {
            text = text.toLowerCase();
            for (UserInfo user : userListFull) {
                if (user.getName().toLowerCase().contains(text) || user.getEmail().toLowerCase().contains(text)) {
                    userList.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        ImageView add_user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            add_user = itemView.findViewById(R.id.add_user);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
        }
    }
}
