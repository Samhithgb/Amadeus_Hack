package com.samhith.travelbuddy;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment {
    private double lat,lng;
    private static final int PLACE_PICKER_REQUEST = 1;
    String city;
    ActionProcessButton loc,emer,chat;

    TextView tv,mName,mAddress,mAttributions;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_a, container, false);
        ActionProcessButton loc=(ActionProcessButton)v.findViewById(R.id.location);
        ActionProcessButton emer=(ActionProcessButton)v.findViewById(R.id.Imme);
        ActionProcessButton chat=(ActionProcessButton)v.findViewById(R.id.forum);
        mName = (TextView) v.findViewById(R.id.textView);
        mAddress = (TextView) v.findViewById(R.id.textView2);
        mAttributions = (TextView) v.findViewById(R.id.textView3);
        tv=(TextView)v.findViewById(R.id.loca);

        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getActivity(),EmergencyActivity.class);
                startActivity(i);
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city.equals("")){
                    Toast.makeText(getActivity(),"Please enter your city!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("cityname", city);
                    startActivity(i);
                }
            }
        });

        return v;

    }
    @Override
    public void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            final String placeid = place.getId();
            final Uri placeuri = place.getWebsiteUri();
            final LatLng latilongi = place.getLatLng();
            lat = latilongi.latitude;
            lng = latilongi.longitude;


            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            //added to get place name
            /*Toast.makeText(PlacePickerActivity.this, attributions,
                    Toast.LENGTH_LONG).show();*/


            mName.setText(name);

            mAddress.setText(address);
            mAttributions.setText(Html.fromHtml(attributions));
            //added to get place id

            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = gcd.getFromLocation(lat, lng, 1);

                if (addresses.size() > 0) {
                    city=addresses.get(0).getLocality();
                    //System.out.println(addresses.get(0).getLocality());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("" + addresses.get(0).getLocality() + "")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } catch (Exception e) {
            }

        }
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
