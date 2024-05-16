package com.moutamid.exercises.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fxn.stash.Stash;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.FirebaseNotificationSender;
import com.moutamid.exercises.Utils.ResumeDialogClass;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


    }
    public void start(View view) {
        ArrayList<String> tokenList = Stash.getArrayList("tokenList", String.class);
        String title = "Office GYM App";
        String message = Stash.getString("profile_name")+ " challenges you to workout now!";
        FirebaseNotificationSender.sendPushNotification(tokenList, title, message);
//                Calendar calendar = Calendar.getInstance();
//                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//                if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY) {
        int exerciseNo = Stash.getInt("exercise_no", 1);
        int exercise_sets = Stash.getInt("exercise_sets", 1);
        if (exerciseNo != 1 && exercise_sets != 1) {
            ResumeDialogClass cdd = new ResumeDialogClass(MainMenuActivity.this);
            cdd.show();
        } else if (exerciseNo != 1) {
            ResumeDialogClass cdd = new ResumeDialogClass(MainMenuActivity.this);
            cdd.show();
        } else {

            startActivity(new Intent(MainMenuActivity.this, ExerciseActivity.class));
        }
//                } else {
//                    Toast.makeText(getContext(), "You can only start this on Monday, Thursday, or Friday.", Toast.LENGTH_SHORT).show();
//                }
    }
}