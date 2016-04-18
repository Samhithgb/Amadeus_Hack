package com.samhith.travelbuddy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.dd.processbutton.iml.ActionProcessButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {

   public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_b, container, false);

        ActionProcessButton b= (ActionProcessButton)v.findViewById(R.id.next);

        Intent i = new Intent(getActivity(),MainActivity.class);
        startActivity(i);

       return v;
    }

}
