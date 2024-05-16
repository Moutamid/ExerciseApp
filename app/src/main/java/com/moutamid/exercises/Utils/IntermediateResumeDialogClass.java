package com.moutamid.exercises.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.moutamid.exercises.Activities.ExerciseActivity;
import com.moutamid.exercises.Activities.IntermediateExerciseActivity;
import com.moutamid.exercises.R;

public class IntermediateResumeDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    public IntermediateResumeDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.resume);
        TextView startbtn = findViewById(R.id.startbtn);
        TextView cancel = findViewById(R.id.cancel);
        startbtn.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startbtn:
                c.startActivity(new Intent(c, IntermediateExerciseActivity.class));
                break;
            case R.id.cancel:
                Stash.put("exercise_no", 1);
                Stash.put("exercise_sets", 1);
                c.startActivity(new Intent(c, IntermediateExerciseActivity.class));
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
    }
}