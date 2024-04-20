package com.moutamid.exercises.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.moutamid.exercises.databinding.FragmentHistoryBinding;
import com.google.firebase.FirebaseApp;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        FirebaseApp.initializeApp(getContext());
        return binding.getRoot();

    }
}