package com.moutamid.exercises.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.jawline.exercises.DataBase.Exercise.Dao;
import com.jawline.exercises.DataBase.Exercise.ExerciseDatabase;
import com.jawline.exercises.DataBase.Exercise.ExerciseModelClass;
import com.jawline.exercises.R;
import com.jawline.exercises.Utils.Config;
import com.jawline.exercises.Utils.util;
import com.jawline.exercises.databinding.ActivityExcersieBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExcersieActivity extends AppCompatActivity {
    private ActivityExcersieBinding binding;
    int progress1;
    int plan_id;
    MediaPlayer mp;
    ExerciseDatabase db;
    Dao dao;
    CountDownTimer countDownTimer, progress_timer;
    int count = 0;
    List<ExerciseModelClass> allplan;
    int day;
    long lefttime;
    long progress_time;
    int noOfMinutes = (int) (1 * 60 * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.changeStatusBarColor(this);
        binding = ActivityExcersieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        db = ExerciseDatabase.getInstance(this);
        dao = db.Dao();
        plan_id = getIntent().getIntExtra("plan_id", 0);
        day = getIntent().getIntExtra("day", 0);
        allplan = dao.getAllExercises(plan_id);
        String exercise_name = allplan.get(count).getExercise_name();
        binding.name.setText(exercise_name);
        ex_name(exercise_name);
//TODO use ImageView instead og GlideImageView
        //TODO initialize predefine lists
        countDownTimer(noOfMinutes);
        progress_timer(60000);
        vibration_mode();
        int duration = Config.getValue("duration", getApplicationContext());
        if (Config.getValue("rating", this) == 3) {
            Config.storeValue("rating", 1, this);
        }
        binding.vibrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.muteVibration.getVisibility() == View.VISIBLE) {
                    Config.storeValue("vibration", 0, ExcersieActivity.this);
                    binding.muteVibration.setVisibility(View.GONE);
                } else {
                    Config.storeValue("vibration", 1, ExcersieActivity.this);
                    binding.muteVibration.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.volumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.muteVolume.getVisibility() == View.VISIBLE) {
                    Config.storeValue("volume", 0, ExcersieActivity.this);
                    binding.muteVolume.setVisibility(View.GONE);
                } else {
                    Config.storeValue("volume", 1, ExcersieActivity.this);
                    binding.muteVolume.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                progress_timer.cancel();
                vibration_mode();
                binding.bottomSheet.setVisibility(View.VISIBLE);
            }
        });
        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer(lefttime);
                progress_timer(progress_time);
                binding.bottomSheet.setVisibility(View.GONE);
                vibration_mode();
            }
        });
        binding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                progress_timer.cancel();
                countDownTimer.cancel();
                onBackPressed();

            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 4) {
                    count++;
                    String exercise_name = allplan.get(count).getExercise_name();
                    Log.d("data", exercise_name + "");
                    binding.name.setText(exercise_name);
                    ex_name(exercise_name);
                    countDownTimer.cancel();
                    progress_timer.cancel();
                    vibration_mode();
                    countDownTimer(noOfMinutes);
                    progress_timer(60000);
                    goto_rest(exercise_name, count);
                }

            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count >= 1) {
                    count--;
                    String exercise_name = allplan.get(count).getExercise_name();
                    Log.d("data", exercise_name + "");
                    binding.name.setText(exercise_name);
                    ex_name(exercise_name);
                    countDownTimer.cancel();
                    progress_timer.cancel();
                    vibration_mode();
                    countDownTimer(noOfMinutes);
                    progress_timer(60000);
                }
            }
        });
    }

    public void ex_name(String ex_name) {
        Log.d("name", ex_name);
        binding.nameEx.setText(ex_name);

        switch (ex_name) {
            case "Ups & Down Nodes":
                binding.gifImageView.setImageResource(R.drawable.up_down);
                binding.animation.setImageResource(R.drawable.up_down);
                binding.textDetail.setText("\u25CF Keep your back straight and maintain a forward gaze.\n" +
                        "\u25CF Slowly move your head up and down to experience a gentle muscle stretch.\n");
                play_voice(R.raw.up_and_down_nodes);
                break;
            case "Chin Tucks":
                binding.gifImageView.setImageResource(R.drawable.chin_tucks);
                binding.animation.setImageResource(R.drawable.chin_tucks);
                binding.textDetail.setText("\u25CF Sit in a comfortable position.\n" +
                        "\u25CF Tilt your head forward, extending your chin in front of your chest.\n" +
                        "\u25CF Gradually bring your chin backward and downward, aiming to touch the back of your neck with your chin.\n" +
                        "\u25CF Maintain this position for 10 seconds.\n" +
                        "\u25CF Return to the starting position and relax.\n" +
                        "\u25CF Repeat the entire process five times.\n");
                play_voice(R.raw.chin_tuks);

                break;
            case "Upward Chewing":
                binding.gifImageView.setImageResource(R.drawable.upward_chewing);
                binding.animation.setImageResource(R.drawable.upward_chewing);
                binding.textDetail.setText("\u25CF Keep your shoulders and back straight\n" +
                        "\u25CF Tilt your head back and look up at the ceiling\n" +
                        "\u25CF Open and close your jaw (like chewing gum) and feel the skin around your neck and chin tighten.\n" +
                        "\u25CF Hold your jaw while looking at the ceiling for 1 second and release. Keep doing the same procedure for 30 seconds.\n");
                play_voice(R.raw.upward_chewings);
                break;
            case "Extend your Tonge":
                play_voice(R.raw.extend_tongue);
                binding.gifImageView.setImageResource(R.drawable.extend_your_tongue);
                binding.animation.setImageResource(R.drawable.extend_your_tongue);
                binding.textDetail.setText("\u25CF Keep your back straight and look straight ahead\n" +
                        "\u25CF Open your mouth\n" +
                        "\u25CF Stick out your tongue as far as you can.\n" +
                        "\u25CF Hold for 10 seconds, then repeat 3 times.\n");

                break;
            case "Open Mouth Widely":
                binding.gifImageView.setImageResource(R.drawable.open_mouth_widely);
                binding.animation.setImageResource(R.drawable.open_mouth_widely);
                binding.textDetail.setText("\u25CF Open your mouth, stretch to open it wide\n" +
                        "\u25CF Separate your teeth from the upper to lower jaw as much as you can\n" +
                        "\u25CF Spread the mouth as much as you can\n" +
                        "\u25CF Hold for 3 seconds. Slowly return your jaw, lips, and then teeth back to their normal position and repeat for 30 seconds.\n");
                play_voice(R.raw.open_mouth_wide);

                break;
            case "Massage your face":
                binding.gifImageView.setImageResource(R.drawable.message_your_face);
                binding.animation.setImageResource(R.drawable.message_your_face);
                binding.textDetail.setText("\u25CF Use your index and middle finger\n" +
                        "\u25CF Massage your whole face (forehead, chin, cheeks, eyebrows, etc.).\n" +
                        "\u25CF Do it for 30 seconds, then relax.\n");
                play_voice(R.raw.massage_your_face);

                break;
            case "Mouthwash Exercise":
                binding.gifImageView.setImageResource(R.drawable.mouth_wash_exercise);
                binding.animation.setImageResource(R.drawable.mouth_wash_exercise);
                binding.textDetail.setText("\u25CF Fill your mouth with air\n" +
                        "\u25CF Try to create the effect of passing the air from one side of the cheek to the other as if you were using mouthwash.\n" +
                        "\u25CF Continue the exercise for 10 seconds and relax.\n");
                play_voice(R.raw.mouthwash_exercise);

                break;
            case "Tilt Your Head Left & Right":
                binding.gifImageView.setImageResource(R.drawable.tile_your_head_left_righgt);
                binding.animation.setImageResource(R.drawable.tile_your_head_left_righgt);
                binding.textDetail.setText("\u25CF Keep your back straight and look straight ahead\n" +
                        "\u25CF Tilt your head from left to right continuously for 30 seconds.\n");
                play_voice(R.raw.tilt_your_head);

                break;
            case "Pushing the Tongue Outward":
                binding.gifImageView.setImageResource(R.drawable.pushing_tongue_outward);
                binding.animation.setImageResource(R.drawable.pushing_tongue_outward);
                binding.textDetail.setText("\u25CF Keep your back and shoulders straight\n" +
                        "\u25CF Rotate your head to the RIGHT side, pull out your tongue as far as possible, and keep it out for 3 seconds.\n" +
                        "\u25CF Rotate your head to the LEFT side, pull out your tongue as far as possible, and keep it out for 3 seconds.\n" +
                        "\u25CF Continue the exercise for 30 seconds.\n");
                play_voice(R.raw.pushing_the_tongue_outward);
                break;
        }

    }

    @Override
    protected void onStop() {
        countDownTimer.cancel();
        progress_timer.cancel();
        super.onStop();
    }

    public void countDownTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(lefttime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lefttime)), TimeUnit.MILLISECONDS.toSeconds(lefttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lefttime)));
                binding.count.setText(hms);
                if (count == 0) {
                    binding.backButton.setTextColor(Color.parseColor("#B6B3B3"));
                    binding.backButton.setEnabled(false);
                } else if (count == 5) {
                    binding.nextButton.setTextColor(Color.parseColor("#B6B3B3"));
                    binding.nextButton.setEnabled(false);
                } else {
                    binding.backButton.setTextColor(Color.parseColor("#000000"));
                    binding.backButton.setEnabled(true);
                    binding.nextButton.setTextColor(Color.parseColor("#000000"));
                    binding.nextButton.setEnabled(true);
                }
                int i = count + 1;
                binding.num.setText(i + "/6");
                if (lefttime < 31000 && lefttime > 30000) {
                    play_voice(R.raw.half_the_time);
                } else if (lefttime < 4000 && lefttime > 3000) {
                    play_voice(R.raw.three);
                } else if (lefttime < 3000 && lefttime > 2000) {
                    play_voice(R.raw.two);
                } else if (lefttime < 2000 && lefttime > 1000) {
                    play_voice(R.raw.one);
                }
            }

            public void onFinish() {
                if (count <= 4) {
                    count++;
                    String exercise_name = allplan.get(count).getExercise_name();
                    binding.name.setText(exercise_name);
                    ex_name(exercise_name);
                    countDownTimer.cancel();
                    progress_timer.cancel();
                    countDownTimer(noOfMinutes);
                    progress_timer(60000);
                    vibration_mode();
                    goto_rest(exercise_name, count);
                } else {
                    vibration_mode();

                    Intent intent = new Intent(ExcersieActivity.this, DayCompleteActivity.class);
                    intent.putExtra("plan_id", plan_id);
                    intent.putExtra("day", day);
                    startActivity(intent);
                }
                binding.progress.setProgress(progress1);

            }
        }.start();

    }

    public void progress_timer(long time) {
        progress_timer = new CountDownTimer(time, 650) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress_time = millisUntilFinished;
                //this will be done every 1000 milliseconds ( 1 seconds )
                progress1 = (int) ((60000 - millisUntilFinished) / 650);
                binding.progress.setProgress(progress1);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }


    @Override
    protected void onRestart() {
        countDownTimer(lefttime);
        progress_timer(progress_time);
        super.onRestart();
    }

    public void play_voice(int voice) {
        if (Config.getValue("volume", getApplicationContext()) == 0) {
            binding.muteVolume.setVisibility(View.GONE);
            mp = MediaPlayer.create(this, voice);
            mp.start();
        } else {
            binding.muteVolume.setVisibility(View.VISIBLE);
        }
    }

    public void vibration_mode() {
        if (Config.getValue("vibration", getApplicationContext()) == 0) {
            binding.muteVibration.setVisibility(View.GONE);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        } else {
            binding.muteVibration.setVisibility(View.VISIBLE);
        }
    }

    public void goto_rest(String name, int count) {
        Intent intent = new Intent(this, RestTimeActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("count", count);
        startActivity(intent);
    }
}