package com.samhith.travelbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {


    private NavigationDrawerCallbacks mCallbacks;
    public static final String FILE_NAME="test_pref";
    public static final String KEY="user_learned_drawer";
    private int mCurrentSelectedPosition = 0;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavdInstanceState;
    private RecyclerView mDrawerList;




    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(),KEY,"false"));
        if(savedInstanceState!=null){
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavdInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.navigation_recycler_view, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if(mDrawerList!=null)
        mDrawerList.setLayoutManager(layoutManager);
        if(mDrawerList!=null)
        mDrawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
            if(adapter!=null) {
                adapter.setNavigationDrawerCallbacks(this);
                mDrawerList.setAdapter(adapter);
                //selectItem(mCurrentSelectedPosition);
            }
        return view;

    }


    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(containerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }


    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(containerView);
    }
    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem("Profile", getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Contact Us", getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Terms and Conditions", getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Change Password", getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Log Out", getResources().getDrawable(R.drawable.ic_menu_check)));


        return items;
    }










    public void setUp(int id,DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView=getActivity().findViewById(id);
        mDrawerLayout=drawerLayout;







        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {



            }
        };

         if(!mUserLearnedDrawer && !mFromSavdInstanceState){

             mDrawerLayout.openDrawer(containerView);
         }


mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }


    public void saveToPreferences(Context context,String preferenceName,String preferenceValue){

        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.commit();

    }



    public static String readFromPreferences(Context context,String preferenceName,String defaultValue){

        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        return sp.getString(preferenceName,defaultValue);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(containerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(containerView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }





}
