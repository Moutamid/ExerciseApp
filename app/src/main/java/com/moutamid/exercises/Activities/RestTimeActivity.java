package com.moutamid.exercises.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.exercises.MainActivity;
import com.moutamid.exercises.R;
import com.moutamid.exercises.Utils.NotificationHelper;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RestTimeActivity extends AppCompatActivity {
    int noOfMinutes = 1 * 60 * 1000;
    CountDownTimer countDownTimer;
    long lefttime;
    TextView count, name_ex, sets, reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_time);
        count = findViewById(R.id.count);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        name_ex = findViewById(R.id.name_ex);

        int currentExercise = Stash.getInt("exercise_no", 1);
        int currentSet = Stash.getInt("exercise_sets", 1);

        if (currentSet == 1) {
            int noOfMinutes = 10 * 60 * 1000;
            countDownTimer(noOfMinutes);


        } else {
//            int noOfMinutes = 1 * 60 * 1000;
            countDownTimer(noOfMinutes);

        }

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int streak = Stash.getInt("Streak", 0);
        int streak_day = Stash.getInt("Streak_Day", 0);
        Log.d("day", dayOfWeek + "  day" + streak + "  Streak" + streak_day + "streakDay");
        if (dayOfWeek == 2 || dayOfWeek == 5 || dayOfWeek == 6) {
            if (dayOfWeek == 2 && streak_day == 6) {
                Stash.put("Streak_Day", dayOfWeek);
                Stash.put("Streak", streak++);
                Log.d("day", "Condition 1 execute");
            } else if (dayOfWeek == 5 && streak_day == 2) {
                Stash.put("Streak_Day", dayOfWeek);
                Stash.put("Streak", streak++);
                Log.d("day", "Condition 2 execute");
            } else if (dayOfWeek == 6 && streak_day == 5) {
                Stash.put("Streak_Day", dayOfWeek);
                Stash.put("Streak", streak++);
                Log.d("day", "Condition 3 execute");
            } else {
                Stash.put("Streak_Day", dayOfWeek);
                Stash.put("Streak", 1);
                Log.d("day", "Condition 4 execute");
            }
        }

        switch (currentSet) {
            case 1:
                sets.setText("Set One");
                reps.setText("12 Reps");
                break;
            case 2:
                sets.setText("Set Two");
                reps.setText("11 Reps");
                break;
            case 3:
                sets.setText("Set Three");
                reps.setText("10 Reps");
                break;
        }
        switch (currentExercise) {
            case 1:

                name_ex.setText("LATERAL RAISES");
                break;
            case 2:
                name_ex.setText("TRICEPS EXTENSIONS");
                break;
            case 3:

                name_ex.setText("LEG EXTENSIONS");
                break;
            case 4:
                name_ex.setText("LEG CURLS");
                break;
            case 5:
                name_ex.setText("STANDING KICKBACKS");
                break;
        }
    }
    public void countDownTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                if (lefttime < 32938 && lefttime > 30936) {
                    NotificationHelper.showTapNotification(RestTimeActivity.this, "Break Reminder", "Take short breaks, do desk exercises daily.");
                }
                Log.d("lefttieme", lefttime+"");
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(lefttime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lefttime)), TimeUnit.MILLISECONDS.toSeconds(lefttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lefttime)));
                count.setText(hms);
//                 if (lefttime<4000 && lefttime> 3000) {
//                    play_voice(R.raw.three);
//                }else if (lefttime<3000 && lefttime> 2000) {
//                    play_voice(R.raw.two);
//                }else if (lefttime<2000 && lefttime> 1000) {
//                    play_voice(R.raw.one);
//                 }
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(RestTimeActivity.this, ExerciseActivity.class));
                finish();
            }
        }.start();

    }

    public void countDownTimerLast(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                Log.d("lefttieme", lefttime + "");
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(lefttime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lefttime)), TimeUnit.MILLISECONDS.toSeconds(lefttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lefttime)));
                count.setText(hms);
//                if (lefttime < 4000 && lefttime > 3000) {
//                    play_voice(R.raw.three);
//                } else if (lefttime < 3000 && lefttime > 2000) {
//                    play_voice(R.raw.two);
//                } else if (lefttime < 2000 && lefttime > 1000) {
//                    play_voice(R.raw.one);
//                }
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(RestTimeActivity.this, MainActivity.class));
                finish();
            }
        }.start();

    }

    public void play_voice(int voice) {
        MediaPlayer mp = MediaPlayer.create(this, voice);
        mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    private String getDayKey(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            default:
                return "";
        }
    }

}