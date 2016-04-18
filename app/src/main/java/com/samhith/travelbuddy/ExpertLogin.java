package com.samhith.travelbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ExpertLogin extends AppCompatActivity {

    EditText email;
    EditText pass;
    ActionProcessButton but;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_login);
        toolbar=(Toolbar)findViewById(R.id.xloginappbar);
        setSupportActionBar(toolbar);
        email=(EditText)findViewById(R.id.xemail);
        pass=(EditText)findViewById(R.id.xpassword);
        but=(ActionProcessButton)findViewById(R.id.xbtnLogIn);


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                String p= pass.getText().toString();

                Firebase ref = new Firebase("https://mytravelbuddy.firebaseio.com/");
                ref.authWithPassword(em,p, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent i = new Intent(ExpertLogin.this,ExpertLand.class);
                        startActivity(i);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(ExpertLogin.this,"Error.Try Again",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}
