package com.samhith.travelbuddy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

public class Login  extends FirebaseLoginBaseActivity {

    Toolbar toolbar;
    Firebase mFirebaseRef;
    boolean admin=false;

    RelativeLayout r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=(Toolbar)findViewById(R.id.loginappbar);

        setSupportActionBar(toolbar);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(Login.this, IntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        t.start();


        final ActionProcessButton button = (ActionProcessButton) findViewById(R.id.btnLogIn);
        button.setMode(ActionProcessButton.Mode.PROGRESS);
        Firebase.setAndroidContext(this);


        r = (RelativeLayout) findViewById(R.id.rla);

        mFirebaseRef = new Firebase("https://mytravelbuddy.firebaseio.com/");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // no progress
                button.setProgress(0);
// progressDrawable cover 50% of button width, progressText is shown
                button.setProgress(50);
// progressDrawable cover 75% of button width, progressText is shown
                button.setProgress(75);
// completeColor & completeText is shown
                button.setProgress(100);

// you can display endless google like progress indicator
                button.setMode(ActionProcessButton.Mode.ENDLESS);
// set progress > 0 to start progress indicator animation
                button.setProgress(1);

/*
                String em = email.getText().toString();
                String pass = password.getText().toString();

                Firebase ref = new Firebase("https://taylormade.firebaseio.com/");
                ref.authWithPassword(em, pass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Snackbar.make(r, "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(), Snackbar.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Snackbar.make(r, "Error while login", Snackbar.LENGTH_LONG).show();
                    }
                });*/


                showFirebaseLoginPrompt();


                // button.setProgress(0);


            }

        });


        final ActionProcessButton but2 = (ActionProcessButton) findViewById(R.id.btnSignUp);
        //   but2.setMode(ActionProcessButton.Mode.PROGRESS);


        assert but2 != null;
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // no progress
                but2.setProgress(0);
// progressDrawable cover 50% of button width, progressText is shown
                but2.setProgress(50);
// progressDrawable cover 75% of button width, progressText is shown
                but2.setProgress(75);
// completeColor & completeText is shown
                but2.setProgress(100);

// you can display endless google like progress indicator
                but2.setMode(ActionProcessButton.Mode.ENDLESS);
// set progress > 0 to start progress indicator animation
                but2.setProgress(1);


                but2.setMode(ActionProcessButton.Mode.ENDLESS);
                Intent j = new Intent(Login.this, SignUp.class);
                startActivity(j);
                but2.setProgress(0);
            }
        });

        final ActionProcessButton xpterlog=(ActionProcessButton)findViewById(R.id.btnLoginxpert);
        TextView te = (TextView) findViewById(R.id.forgot);
        assert te != null;
        te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
                alertDialog.setTitle("Forgot Password?");
                alertDialog.setMessage("Enter email to send reset instructions");

                final EditText input = new EditText(Login.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog pd = new ProgressDialog(Login.this);
                                pd.setMessage("Sending Instructions");
                                pd.show();
                                String em = input.getText().toString();
                                Firebase ref = new Firebase("https://mytravelbuddy.firebaseio.com/");
                                ref.resetPassword(em, new Firebase.ResultHandler() {
                                    @Override
                                    public void onSuccess() {
                                        pd.dismiss();
                                        Snackbar.make(r, "Instructions sent successfully", Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {
                                        pd.dismiss();
                                        Snackbar.make(r, "Error.Try Again.", Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


        assert xpterlog != null;
        xpterlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i= new Intent(Login.this,ExpertLogin.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        r= (RelativeLayout)findViewById(R.id.rla);
        Snackbar.make(r, "Error from the provider.Try Again or Use different Login method.", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        r= (RelativeLayout)findViewById(R.id.rla);
        Snackbar.make(r, "User Error.Please Try Again.", Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void onFirebaseLoggedIn(AuthData authData) {

            Toast.makeText(Login.this,"Hey!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Login.this,LandingScreen.class);
            startActivity(i);



    }



    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
//        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
    }

}