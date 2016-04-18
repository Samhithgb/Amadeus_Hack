package com.samhith.travelbuddy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

      @Override
    public void init(@Nullable Bundle savedInstanceState) {
          addSlide(AppIntroFragment.newInstance("It's Simple!", "Just post your question", R.drawable.tb, R.color.colorPrimaryDark));
          addSlide(AppIntroFragment.newInstance("It's easy!", "Chat with our experts if you are in trouble!", R.drawable.tb, R.color.colorPrimaryDark));
          addSlide(AppIntroFragment.newInstance("It's quick!", "Lot of experts and locallites here to help you!", R.drawable.tb, R.color.colorPrimaryDark));
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

    }

    @Override
    public void onSlideChanged() {

    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
