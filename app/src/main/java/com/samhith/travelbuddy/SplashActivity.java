package com.samhith.travelbuddy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class SplashActivity extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Firebase.setAndroidContext(this);

        StartAnimations();

    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Firebase ref = new Firebase("https://mytravelbuddy.firebaseio.com/");
                    ref.addAuthStateListener(new Firebase.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(AuthData authData) {
                            if (authData != null) {
                                Toast.makeText(SplashActivity.this,"User Logged in succesfully",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SplashActivity.this, LandingScreen.class);
                                startActivity(i);


                            } else {
                                Intent j = new Intent(SplashActivity.this, Login.class);
                                startActivity(j);
                            }
                        }
                    });
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }

}