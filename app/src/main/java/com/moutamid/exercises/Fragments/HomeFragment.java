package com.moutamid.exercises.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.moutamid.exercises.Activities.AllExercisesActivity;
import com.moutamid.exercises.Activities.ExerciseActivity;
import com.moutamid.exercises.Activities.OverviewActivity;
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

        binding.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     startActivity(new Intent(getContext(), AllExercisesActivity.class));
               }
        });   binding.startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY) {
                    startActivity(new Intent(getContext(), ExerciseActivity.class));
                } else {
                    Toast.makeText(getContext(), "You can only start this on Monday, Thursday, or Friday.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }



}