//package com.moutamid.exercises.Activities;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.fxn.stash.Stash;
//import com.moutamid.exercises.DataBase.ExerciseDbHelper;
//import com.moutamid.exercises.DataBase.User;
//import com.moutamid.exercises.R;
//
//public class ExerciseActivity extends AppCompatActivity {
//    private VideoView videoView;
//    private ImageView play, pause;
//    private TextView timerTextView, reps, next_btn, name, details, sets;
//    private long startTime = 0;
//    String videoPath;
//    private Handler timerHandler = new Handler();
//    double MET;
//    double calories_burn;
//    private int currentExercise;
//    private int currentSet;
//    String weight;
//    int minutes;
//    private ExerciseDbHelper dbHelper;
//
//    private Runnable timerRunnable = new Runnable() {
//        @Override
//        public void run() {
//            long millis = System.currentTimeMillis() - startTime;
//            int seconds = (int) (millis / 1000);
//            minutes = seconds / 60;
//            seconds = seconds % 60;
//            double v = MET * 3.5 * Integer.parseInt(weight);
//            double v1 = v / 200;
//            calories_burn = v1 * Double.parseDouble(String.valueOf(minutes));
//            Log.d("dataaa", v + "   "+minutes+"     " + v1 + "  " + calories_burn);
//            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
//            timerHandler.postDelayed(this, 200);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
//        dbHelper = new ExerciseDbHelper(this);
//        name = findViewById(R.id.name);
//        details = findViewById(R.id.details);
//        sets = findViewById(R.id.sets);
//        next_btn = findViewById(R.id.next_btn);
//        videoView = findViewById(R.id.video_view);
//        timerTextView = findViewById(R.id.duration);
//        reps = findViewById(R.id.reps);
//        currentExercise = Stash.getInt("exercise_no", 1);
//        currentSet = Stash.getInt("exercise_sets", 1);
//        User user = (User) Stash.getObject("user", User.class);
//        weight = user.weight;
//        play = findViewById(R.id.play);
//        pause = findViewById(R.id.pause);
//        switch (currentSet) {
//            case 1:
//                sets.setText("One");
//                reps.setText("12");
//                break;
//            case 2:
//                sets.setText("Two");
//                reps.setText("11");
//                break;
//            case 3:
//                sets.setText("Three");
//                reps.setText("10");
//                break;
//        }
//        switch (currentExercise) {
//            case 1:
//
//                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
//                name.setText("LATERAL RAISES");
//                details.setText("While reviewing workload and planning day");
//                MET = 3;
//                break;
//            case 2:
//                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video2;
//                name.setText("TRICEPS EXTENSIONS");
//                details.setText("While reading, sorting, prioritizing, saving emails");
//                MET = 3;
//                break;
//            case 3:
//                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video3;
//
//                name.setText("LEG EXTENSIONS");
//                details.setText("while responding to correspondence/initiating contacts");
//                MET = 3.5;
//                break;
//            case 4:
//                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video4;
//                name.setText("LEG CURLS");
//                details.setText("While continuing correspondence replies and outreach");
//                MET = 3.5;
//                break;
//            case 5:
//                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video5;
//                name.setText("STANDING KICKBACKS");
//                details.setText("As you prepare to begin you first task of the day");
//                MET = 3;
//                break;
//        }
//        videoView.setVideoURI(Uri.parse(videoPath));
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                // Start the video again when it completes
//                videoView.start();
//                next_btn.setVisibility(View.VISIBLE);
//            }
//        });
//        videoView.start();
//
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!videoView.isPlaying()) {
//                    pause.setVisibility(View.VISIBLE);
//                    play.setVisibility(View.INVISIBLE);
//                    videoView.start();
//                    startTime = System.currentTimeMillis() - videoView.getCurrentPosition();
//                    timerHandler.postDelayed(timerRunnable, 0);
//                }
//            }
//        });
//
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (videoView.isPlaying()) {
//                    play.setVisibility(View.VISIBLE);
//                    pause.setVisibility(View.INVISIBLE);
//                    videoView.pause();
//                    timerHandler.removeCallbacks(timerRunnable);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        startTime = System.currentTimeMillis();
//        timerHandler.postDelayed(timerRunnable, 0);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        timerHandler.removeCallbacks(timerRunnable);
//    }
//
//    public void next(View view) {
//        storeExerciseData(name.getText().toString() + " (Set " + currentSet + ")", minutes, currentSet);
//
//        if (currentSet == 3) {
//            currentExercise++; // Move to next exercise
//            currentSet = 1; // Reset set to 1
//        } else {
//            currentSet++;
//        }
//
//        Stash.put("exercise_no", currentExercise);
//        Stash.put("exercise_sets", currentSet);
//
//        startActivity(new Intent(this, RestTimeActivity.class));
//        finish();
//    }
//
//    public void back(View view) {
//        onBackPressed();
//    }
//
//    private void storeExerciseData(String exerciseName, int minutes, int set) {
//        long newRowId = dbHelper.addExercise(exerciseName, minutes, calories_burn);
//    }
//}
package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.exercises.DataBase.User;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.ExerciseManager;

public class AdvancedExerciseActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 60000; // 1 minute, adjust as needed

    private ImageView videoView;
    private ImageView play, pause;
    private TextView timerTextView, reps, next_btn, name, details, sets;
    private long startTime = System.currentTimeMillis();
    String videoPath;
    private Handler timerHandler = new Handler();
    double MET;
    double calories_burn;
    private int currentExercise;
    private int currentSet;
    String weight;
    int minutes;
    private long pauseTime;
    private boolean timerRunning;
    final long[] timeRemaining = {0};

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
//            startTime=millis;
            int seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            double v = MET * 3.5 * Double.parseDouble(weight);
            double v1 = v / 200;
            calories_burn = v1 * Double.parseDouble(String.valueOf(minutes));
            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 200);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        name = findViewById(R.id.name);
        details = findViewById(R.id.details);
        sets = findViewById(R.id.sets);
        next_btn = findViewById(R.id.next_btn);
        videoView = findViewById(R.id.video_view);
        timerTextView = findViewById(R.id.duration);
        reps = findViewById(R.id.reps);
        currentExercise = Stash.getInt("exercise_no", 1);
        currentSet = Stash.getInt("exercise_sets", 1);
        Log.d("dataaa", currentExercise + "   " + currentSet);

        User user = (User) Stash.getObject("user", User.class);
        if (user.weight_type.equals("LB")) {
            String weight1 = user.weight;
            double v = Double.parseDouble(weight1) * 0.453592;
            weight = String.valueOf(v);


        } else {
            weight = user.weight;

        }
        Log.d("weight", weight + "    " + user.weight_type);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        if (!timerRunning) {
            startTime = System.currentTimeMillis() - timeRemaining[0];
            timerHandler.postDelayed(timerRunnable, 0);
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            timerRunning = true;
        }


        switch (currentSet) {
            case 1:
                sets.setText("One");
                reps.setText("13");
                break;
            case 2:
                sets.setText("Two");
                reps.setText("14");
                break;
            case 3:
                sets.setText("Three");
                reps.setText("15");
                break;
        }
        switch (currentExercise) {
            case 1:
                videoView.setImageResource(R.drawable.img1);
                name.setText("LATERAL RAISES");
                MET = 3;
                break;
            case 2:
                videoView.setImageResource(R.drawable.img3);
                name.setText("TRICEPS EXTENSIONS");
                MET = 3;
                break;
            case 3:
                videoView.setImageResource(R.drawable.img2);
                name.setText("LEG EXTENSIONS");
                MET = 3.5;
                break;
            case 4:
                videoView.setImageResource(R.drawable.img4);
                name.setText("LEG CURLS");
                MET = 3.5;
                break;
            case 5:
                videoView.setImageResource(R.drawable.img5);
                name.setText("STANDING KICKBACKS");
                MET = 3;
                break;
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    startTime = System.currentTimeMillis() - timeRemaining[0];
                    timerHandler.postDelayed(timerRunnable, 0);
                    play.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
                    timerRunning = true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    timerHandler.removeCallbacks(timerRunnable);
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                    timerRunning = false;
                    timeRemaining[0] = System.currentTimeMillis() - startTime;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        startTime = System.currentTimeMillis();
//        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void next(View view) {
        storeExerciseData(name.getText().toString() + " (Set " + currentSet + ")", minutes, currentSet);
        if (currentSet == 3 && currentExercise == 5) {
            Stash.put("exercise_no", 1);
            Stash.put("exercise_no", 1);
//            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else {
            startActivity(new Intent(this, RestTimeActivity.class));
            finish();
        }
        if (currentSet == 3) {
            currentExercise++; // Move to next exercise
            currentSet = 1; // Reset set to 1
        } else {
            currentSet++;
        }

        Stash.put("exercise_no", currentExercise);
        Stash.put("exercise_sets", currentSet);

    }

    public void back(View view) {
        onBackPressed();
    }

    private void storeExerciseData(String exerciseName, int minutes, int set) {
        ExerciseManager exerciseManager = new ExerciseManager();
        exerciseManager.addExercise(exerciseName, minutes, calories_burn);
    }


}
