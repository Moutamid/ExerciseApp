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
import com.moutamid.exercises.Activities.EditProfileActivity;
import com.moutamid.exercises.Activities.LoginActivity;
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
    List<User> usersModels = new ArrayList<>();

    Context mcontext;
    String name, gender, weight;
    int age;
    int i = (int) (0.33 * 60 * 1000);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        String userName = Stash.getString("user_name");

        binding.textView6.setText(userName);


        char c = userName.charAt(0);
        binding.textView5.setText(c + "");
        vibration_mode();
        voice_mode();

//        new DatabaseAsyncTask().execute();
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
        binding.voiceBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Config.storeValue("volume", 0, getContext());
                    binding.voiceBtn.setChecked(true);
                } else {
                    Config.storeValue("volume", 1, getContext());
                    binding.voiceBtn.setChecked(false);
                }
            }
        });
        binding.restDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showBottomSheetDialog();

            }
        });

        binding.vibrationBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Config.storeValue("vibration", 0, getContext());
                    binding.vibrationBtn.setChecked(true);
                } else {
                    Config.storeValue("vibration", 1, getContext());
                    binding.vibrationBtn.setChecked(false);

                }
            }
        });
        return binding.getRoot();

    }

//    class DatabaseAsyncTask extends AsyncTask<Void, Void, List<User>> {
//        @Override
//        protected List<User> doInBackground(Void... voids) {
//            // Perform the database operation on the background thread
//            return userDao.getUser();
//        }
//
//        @Override
//        protected void onPostExecute(List<User> users)
//        {
//            super.onPostExecute(users);
//             name = users.get(0).name.toString();
//             gender = users.get(0).gender.toString();
//             age = users.get(0).age;
//             weight = users.get(0).weight.toString();
//            binding.textView6.setText(users.get(0).name);
//            char c = users.get(0).name.charAt(0);
//            binding.textView5.setText(c + "");
//        }
//   }

    public void voice_mode() {
        if (Config.getValue("volume", getContext()) == 0) {
            binding.voiceBtn.setChecked(true);
        } else {
            binding.voiceBtn.setChecked(false);
        }
    }

    public void vibration_mode() {
        if (Config.getValue("vibration", getContext()) == 0) {
            binding.vibrationBtn.setChecked(true);

        } else {
            binding.vibrationBtn.setChecked(false);
        }
    }

    private void initRecyclerView(NumberPicker numberPicker) {
        String[] data = {"10s", "15s", "Default", "25s", "30s"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(3);
        numberPicker.setSelectedTypeface(Typeface.create(getString(R.string.calibri_bold), Typeface.BOLD));
        numberPicker.setAccessibilityDescriptionEnabled(true);
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Click on current value");
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", picker.getWheelItemCount() + "  " + String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                i = (int) rest_duration(newVal);

            }
        });

    }

    public double rest_duration(int value) {
        switch (value) {
            case 1:
                return 0.16 * 60 * 1000;
            case 2:
                return 0.25 * 60 * 1000;
            case 3:
                return 0.33 * 60 * 1000;
            case 4:
                return 0.41 * 60 * 1000;
            case 5:
                return 0.5 * 60 * 1000;
        }
        return value;
    }


}
