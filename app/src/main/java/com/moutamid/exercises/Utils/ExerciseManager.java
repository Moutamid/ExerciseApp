package com.moutamid.exercises.Utils;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moutamid.exercises.DataBase.Exercise;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseManager {
    public  DatabaseReference exercisesRef;
    public  DatabaseReference exercisesRefBuddies;

    public ExerciseManager() {
      exercisesRef=  FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Exercises");
        exercisesRefBuddies=  FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Buddies");
    }

    public void addExercise(String exerciseName, int minutes, double caloriesBurned) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());
        Exercise exercise = new Exercise(exerciseName, minutes, caloriesBurned, currentDate, Stash.getString("profile_name"));
        exercisesRef.push().setValue(exercise);
    }
    public  Query getExercisesByDate(String date) {
        return exercisesRef.orderByChild("date").equalTo(date);
    }  public  Query getExercisesByDateBuddies(String date) {
        return exercisesRefBuddies.orderByChild("date").equalTo(date);
    }


}
