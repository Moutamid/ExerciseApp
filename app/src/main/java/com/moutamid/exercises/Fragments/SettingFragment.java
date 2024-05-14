package com.moutamid.exercises.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.moutamid.exercises.Activities.BuddiesActivity;
import com.moutamid.exercises.Activities.EditProfileActivity;
import com.moutamid.exercises.Authentication.LoginActivity;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.Config;
import com.moutamid.exercises.Utils.DeleteDialogClass;
import com.moutamid.exercises.Utils.FeedBackDialogClass;
import com.moutamid.exercises.Utils.RateDialogClass;
import com.moutamid.exercises.databinding.FragmentSettingBinding;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        String userName = Stash.getString("profile_name");

        binding.textView6.setText(userName);


        char c = userName.charAt(0);
        binding.textView5.setText(c + "");

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c == 'G') {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    Intent intent = new Intent(getContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        binding.rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage;
                    shareMessage = "Office GYM\n Portable Fitness System\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        binding.rlRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateDialogClass cdd = new RateDialogClass(getActivity());
                cdd.show();
            }
        });
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedBackDialogClass cdd = new FeedBackDialogClass(getActivity());
                cdd.show();
            }
        });
        binding.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDialogClass cdd = new DeleteDialogClass(getActivity());
                cdd.show();
            }
        });

        binding.restDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BuddiesActivity.class);
                startActivity(intent);

            }
        });
        return binding.getRoot();

    }


}
