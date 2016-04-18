package com.samhith.travelbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUp extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);
        final RelativeLayout rla= (RelativeLayout)findViewById(R.id.rlayout);
        final EditText email= (EditText)findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText3);
        final EditText repassword= (EditText)findViewById(R.id.editText4);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText city=(EditText)findViewById(R.id.city);
        final EditText expert=(EditText)findViewById(R.id.expert);
        final ActionProcessButton button = (ActionProcessButton) findViewById(R.id.btnSignIn);
        button.setMode(ActionProcessButton.Mode.PROGRESS);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(SignUp.this);
                pd.setMessage("Adding You");
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


               final String e = email.getText().toString();
                final String p = password.getText().toString();
                String r = repassword.getText().toString();
                final String unme=username.getText().toString();
                final String cty =city.getText().toString();
                final int exp=Integer.parseInt(expert.getText().toString());

                if (!p.equals(r)) {
                    pd.dismiss();
                    password.setError("Passwords Don't Match!");
                } else {

                   final Firebase ref = new Firebase("https://mytravelbuddy.firebaseio.com/");
                    ref.createUser(e, p, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            final userData ud=new userData();
                            ud.setCity(cty);
                            ud.setName(unme);
                            ud.setExpert(exp);
                            ref.authWithPassword(e, p, new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    ref.child("users").child(ref.getAuth().getUid()).setValue(ud);
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    Snackbar.make(rla, "Error. Please Try Again ", Snackbar.LENGTH_SHORT).show();

                                }
                            });

                            Snackbar.make(rla, "Successfully created user account with uid: " + result.get("uid"), Snackbar.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this, LandingScreen.class);
                            startActivity(i);


                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            pd.dismiss();
                            String s = firebaseError.getDetails();
                            Snackbar.make(rla, "Error.Please Try Again.", Snackbar.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });








    }
}
