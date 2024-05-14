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
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.exercises.Activities.SplashActivity;
import com.moutamid.exercises.R;

public class DeleteDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    public DeleteDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.delete);
        TextView startbtn = findViewById(R.id.startbtn);
        TextView cancel = findViewById(R.id.cancel);
        startbtn.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startbtn:
                Stash.clear("user");
//                 Config.storeValue("Day", 1, c);
//                LoginManager.getInstance().logOut();
//                GoogleSignInOptions gso = new GoogleSignInOptions.
//                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                        build();
//                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(c, gso);
//                googleSignInClient.signOut();
//                Config.storeValue("is_login", 1, c);
                FirebaseAuth.getInstance().signOut();
                c.startActivity(new Intent(c, SplashActivity.class));
                c.finishAffinity();
                break;
            case R.id.cancel:
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
    }
}