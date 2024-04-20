package com.moutamid.exercises.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.moutamid.exercises.Activities.LoginActivity;
import com.moutamid.exercises.DataBase.AppDatabase;
import com.moutamid.exercises.DataBase.UserDao;
import com.moutamid.exercises.R;

public class DeleteDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    AppDatabase db1;
    UserDao userDao;

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
        db1 = AppDatabase.getDatabase(getContext());
        userDao = db1.userDao();
        startbtn.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startbtn:
                 Config.storeValue("Day", 1, c);
                LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(c, gso);
                googleSignInClient.signOut();
                Config.storeValue("is_login", 1, c);

                c.startActivity(new Intent(c, LoginActivity.class));
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