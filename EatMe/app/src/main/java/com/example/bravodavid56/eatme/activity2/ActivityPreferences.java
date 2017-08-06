package com.example.bravodavid56.eatme.activity2;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bravodavid56.eatme.R;
import com.example.bravodavid56.eatme.data.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by bravodavid56 on 7/29/2017.
 * Updated by Angel on 8/2/2017
 */


public class ActivityPreferences extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback {

    private String TAG = "preferencesTag";
    private Location mLastLocation;
    private Button button;
    private Button startButton;
    private TextView addressText;
    private EditText edit;
    double latitude;
    double longitude;
    LocationHelper locationHelper;
    private Spinner spinnerPrice;
    private Spinner spinnerRating;



//    private boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();


        button = (Button) findViewById(R.id.locateButton);
        startButton = (Button) findViewById(R.id.enterButton);

        // the layout for this activity is under res->layout->activity_preferences.xml
        TextView logoText = (TextView) findViewById(R.id.textViewLabel);
        logoText.setText("You are located at: ");
        addressText = (TextView) findViewById(R.id.textView2);

        spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);
        edit = (EditText) findViewById(R.id.select_place);


        final ArrayAdapter<CharSequence> spinnerPriceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_range_array, android.R.layout.simple_spinner_item);
        spinnerPriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(spinnerPriceAdapter);

        final ArrayAdapter<CharSequence> spinnerRatingAdapter = ArrayAdapter.createFromResource(this,
                R.array.rating_range_array, android.R.layout.simple_spinner_item);
        spinnerRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(spinnerRatingAdapter);






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runAddress();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String price = spinnerPrice.getSelectedItem().toString();

                String rating = spinnerRating.getSelectedItem().toString();

                String placeEdit = edit.getText().toString();

                Intent sendDataToFragment = new Intent(ActivityPreferences.this, FragmentPreferences.class);


                Bundle args = new Bundle();
                args.putString("Place", placeEdit);
                args.putString("Price", price);
                args.putString("Rating", rating);


//                sendDataToFragment.putExtra("Place", placeEdit);
//                sendDataToFragment.putExtra("Price", price);
//                sendDataToFragment.putExtra("Rating", rating);
//                startActivity(sendDataToFragment);



                FragmentManager fm = getSupportFragmentManager();
                FragmentPreferences frag = new FragmentPreferences();
                frag.setArguments(args);
                frag.show(fm, "FragmentPreferences");
            }
        });

//        textView.setText("You clicked Preferences");
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showToast("Proceed to the next step");
//
//
//                String label = "Restauraunt Title";
//                String uriBegin = "geo:" + latitude + "," + longitude;
//                String query = latitude + "," + longitude + "(" + label + ")";
//                String encodedQuery = Uri.encode(query);
//                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
//                Uri uri = Uri.parse(uriString);
//                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//                startActivity(mapIntent);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
//            }
//        });
//
//        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();


        }





    }

public void runAddress(){
    mLastLocation = locationHelper.getLocation();

    if (mLastLocation != null) {
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();
        getAddress();

    } else {
//            start = false;
        if(button.isEnabled())
            button.setEnabled(false);

        showToast("Couldn't get the location. Make sure location is enabled on the device");
    }

}


    public void getAddress() {
        Address locationAddress;

        locationAddress = locationHelper.getAddress(latitude, longitude);

        if (locationAddress != null) {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation;
            String addr;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;
                addr = address;

//                if (!TextUtils.isEmpty(address1))
//                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;
                    addr += " " + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " " + postalCode;
                        addr += " " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                        addr += " " + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;
                    addr += " " + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;
                    addr += " " + country;
                Log.d(addr,"check");
                addressText.setText(currentLocation);
                addressText.setVisibility(View.VISIBLE);
                Log.d(TAG, "Address");
                Log.d(currentLocation, "here");




                if (!button.isEnabled()) {
                    button.setEnabled(true);
                }

            } else
                showToast("Something went wrong");
        }
    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }


}
