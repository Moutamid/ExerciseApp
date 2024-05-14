package com.moutamid.exercises.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.exercises.DataBase.Exercise;
import com.moutamid.exercises.R;

import java.util.List;

public class ExerciseBuddiesAdapter extends RecyclerView.Adapter<ExerciseBuddiesAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList;

    public ExerciseBuddiesAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_buddy_track, parent, false);
        return new ExerciseViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseNameTextView;
        private TextView minutesTextView, user_name;
        private TextView caloriesBurnedTextView;


        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            exerciseNameTextView = itemView.findViewById(R.id.name);
            minutesTextView = itemView.findViewById(R.id.time);
            caloriesBurnedTextView = itemView.findViewById(R.id.caloriesBurnedTextView);
        }

        void bind(Exercise exercise) {
            user_name.setText(exercise.user_name);
            exerciseNameTextView.setText(exercise.getExerciseName());
            if (exercise.getMinutes() < 1) {
                minutesTextView.setText("Less than a minute");
            } else {
                minutesTextView.setText(String.valueOf(exercise.getMinutes()) + " minutes");

            }
            double value = exercise.getCaloriesBurned();
            String formattedValue = String.format("%.2f", value);

            caloriesBurnedTextView.setText(formattedValue+ " calories");
        }
    }
}
