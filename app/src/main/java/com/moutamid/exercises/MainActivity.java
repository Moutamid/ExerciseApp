package com.moutamid.exercises;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.moutamid.exercises.Fragments.HistoryFragment;
import com.moutamid.exercises.Fragments.HomeFragment;
import com.moutamid.exercises.Fragments.SettingFragment;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    final int PERMISSION_REQUEST_CODE = 112;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        checkApp(MainActivity.this);
        setContentView(view);
        if (Build.VERSION.SDK_INT > 32) {
            if (!shouldShowRequestPermissionRationale("112")) {
                getNotificationPermission();
            }
        }
        navController = Navigation.findNavController(this, R.id.main_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.bottomBar.setActiveItem(0);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment newFragment = new HomeFragment();
        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (i == 0) {
                    Fragment newFragment = new HomeFragment();
                    transaction.replace(R.id.main_fragment, newFragment);

                } else if (i == 1) {
                    Fragment newFragment = new HistoryFragment();
                    transaction.replace(R.id.main_fragment, newFragment);

                } else if (i == 2) {
                    Fragment newFragment = new SettingFragment();
                    transaction.replace(R.id.main_fragment, newFragment);

                }
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (binding.bottomBar.getActiveItem() == 0) {
            finish();
        } else {
            binding.bottomBar.setActiveItem(0);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment newFragment = new HomeFragment();
            transaction.replace(R.id.main_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    public void getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_CODE);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // allow

                } else {
                    Toast.makeText(this, "Permisssion denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }

    }

    public static void checkApp(Activity activity) {
        String appName = "Exercise App";

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

}