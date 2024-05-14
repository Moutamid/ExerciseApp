package com.moutamid.exercises.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.moutamid.exercises.Activities.AllExercisesActivity;
import com.moutamid.exercises.Activities.ExerciseActivity;
import com.moutamid.exercises.Activities.MainMenuActivity;
import com.moutamid.exercises.Activities.OverviewActivity;
import com.moutamid.exercises.Utils.NotificationHelper;
import com.moutamid.exercises.Utils.NotificationScheduler;
import com.moutamid.exercises.Utils.ResumeDialogClass;
import com.moutamid.exercises.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.overviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getContext(), OverviewActivity.class));
            }
        });
        binding.streak.setText(Stash.getInt("Streak", 0)+"");

        binding.startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  startActivity(new Intent(getContext(), AllExercisesActivity.class));
               }
        });
        binding.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainMenuActivity.class));

            }
        });

        return binding.getRoot();
    }



}