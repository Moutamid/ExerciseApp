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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_track, parent, false);
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
        private TextView minutesTextView;
        private TextView caloriesBurnedTextView;


        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.name);
            minutesTextView = itemView.findViewById(R.id.time);
            caloriesBurnedTextView = itemView.findViewById(R.id.caloriesBurnedTextView);
        }

        void bind(Exercise exercise) {
            exerciseNameTextView.setText(exercise.getExerciseName());
            if(exercise.getMinutes()<1)
            {
                minutesTextView.setText("Less than a minute");

            }
else
            {
                minutesTextView.setText(String.valueOf(exercise.getMinutes())+" minutes");

            }
            caloriesBurnedTextView.setText(String.valueOf(exercise.getCaloriesBurned())+" calories");
        }
    }
}
