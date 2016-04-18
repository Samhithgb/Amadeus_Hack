package com.samhith.travelbuddy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import tabs.SlidingTabLayout;
import com.firebase.client.Firebase;

public class LandingScreen extends AppCompatActivity  implements NavigationDrawerCallbacks  {

    Firebase mFirebaseRef;
    private NavigationDrawerFragment drawerFragment;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mtabs;

    private DrawerLayout dl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        toolbar=(Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        mFirebaseRef=new Firebase("https://mytravelbuddy.firebaseio.com/");
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        drawerFragment=(NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);
        drawerFragment.setUp(R.id.nav_drawer_fragment,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar );


        mPager=(ViewPager)findViewById(R.id.viewPager);
        mtabs=(SlidingTabLayout)findViewById(R.id.tab);

        if(mPager!=null)
            mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //else Toast.makeText(LandingScreen.this,"mPager is null",Toast.LENGTH_SHORT).show();
        if(mtabs!=null){
            mtabs.setViewPager(mPager);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        // Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        if(position==0){
         //   Toast.makeText(LandingScreen.this, "Coming Soon", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LandingScreen.this,ProfileDisplay.class);
                    startActivity(i);

        }

        if(position==1){
            Toast.makeText(LandingScreen.this,"Coming Soon",Toast.LENGTH_LONG).show();

        }
        if(position==2){
            Toast.makeText(LandingScreen.this, "Coming Soon", Toast.LENGTH_LONG).show();

            //Intent i = new Intent(LandingScreen.this,ChangePassword.class);
         //   startActivity(i);


        }

        if(position==3){
            mFirebaseRef.unauth();
            Intent i = new Intent(LandingScreen.this,Login.class);
            startActivity(i);
        }


    }


    @Override
    public void onBackPressed() {
        if (drawerFragment.isDrawerOpen())
            drawerFragment.closeDrawer();
        else{
            android.os.Process.killProcess(android.os.Process.myPid());

        }}


    class MyPagerAdapter extends FragmentPagerAdapter {


        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.TabTitle1);
        }



        @Override
        public Fragment getItem(int position) {

            Fragment f=null;

            if(position==0){
                f= new FragmentA();
            }

            if(position==1){
                f=new FragmentB();
            }

            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }





}