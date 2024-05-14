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
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.exercises.Activities.IntroActivity;
import com.moutamid.exercises.Fragments.HistoryFragment;
import com.moutamid.exercises.Fragments.HomeFragment;
import com.moutamid.exercises.Fragments.SettingFragment;
import com.moutamid.exercises.Utils.FirebaseNotificationSender;
import com.moutamid.exercises.Utils.NotificationHelper;
import com.moutamid.exercises.Utils.NotificationScheduler;
import com.moutamid.exercises.Utils.util;
import com.moutamid.exercises.databinding.ActivityIntroBinding;
import com.moutamid.exercises.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    final int PERMISSION_REQUEST_CODE = 112;
    private ActivityMainBinding binding;
    String result;
    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    private List<String> tokenList;
    private List<String> userIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NotificationHelper.createNotificationChannel(MainActivity.this);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                result = task.getResult();
                Map<String, Object> updates = new HashMap<>();
                updates.put("token", result);

                FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                }).addOnFailureListener(new OnFailureListener(


                ) {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users"); // Adjust this path to match your database structure
        tokenList = new ArrayList<>();
        userIdList = new ArrayList<>();

        fetchTokens();
        checkApp(MainActivity.this);
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
//        NotificationHelper.createNotificationChannel(MainActivity.this);
        NotificationScheduler.scheduleNotifications(this);

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
//            transaction.addToBackStack(null);
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
    private void fetchTokens() {
        DatabaseReference databaseReferenceBuddies = FirebaseDatabase.getInstance().getReference().child("OfficeGymApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Buddies");

        databaseReferenceBuddies.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String token = snapshot.child("token").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    if (token != null) {
                        tokenList.add(token);
                        userIdList.add(id);
                    }
                }
                Stash.put("tokenList", tokenList);
                Stash.put("userIdList", userIdList);
                for (String token : userIdList) {
                    Log.d("TAG", "Token: " + userIdList);
                }
              }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadTokens:onCancelled", databaseError.toException());
            }
        });
    }
    private void sendFCMPush(String token) {
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", "Admin");
            notifcationBody.put("message", "Send a new message");
            notification.put("to", token);
            notification.put("data", notifcationBody);
            Log.e("DATAAAAAA", notification.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.NOTIFICATIONAPIURL, notification,
//                response -> {
//                    Log.e("True", response + "");
////                    Toast.makeText(BookingActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("Responce", response.toString());
//                },
//                error -> {
//                    Log.e("False", error + "");
//                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", "key=" + Constants.ServerKey);
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        int socketTimeout = 1000 * 60;// 60 seconds
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsObjRequest.setRetryPolicy(policy);
//        requestQueue.add(jsObjRequest);
    }

}