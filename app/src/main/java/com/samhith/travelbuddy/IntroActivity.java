package com.samhith.travelbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {


    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("It's Simple!", "Just ask your question", R.drawable.qp, R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance("It's easy!", "Get it answered by local experts!", R.drawable.kn, R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance("It's quick!", "Make your travel hassle free!", R.drawable.tr, R.color.colorPrimary));
        setFadeAnimation();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
    }



    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }


    private void loadMainActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
