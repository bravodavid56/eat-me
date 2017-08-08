package com.example.bravodavid56.eatme.data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by angel on 8/2/17.
 * Uses the Google API Client to check permissions
 * Android API said to use this method instead of
 * Followed online example
 */

//Note:
    //Note: The strategies described in this guide apply to the platform location API in android.location.
// The Google Location Services API, part of Google Play Services, provides a more powerful,
// high-level framework that automatically handles location providers, user movement, and location accuracy.
// It also handles location update scheduling based on power consumption parameters you provide.
// In most cases, you'll get better battery performance, as well as more appropriate accuracy,
// by using the Location Services API.

public class LocationHelper implements PermissionUtility.PermissionResultCallback{
    private String TAG = "LocationTag";
    private Context context;
    private Activity map_actvity;

    private boolean permissionGranted;

    private Location lastKnownLocation;

    // Google API Client

    private GoogleApiClient googleApiClient;

    // Permissions List
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private PermissionUtility permissionUtils;

    public LocationHelper(Context context) {

        this.context=context;
        this.map_actvity= (Activity) context;

        permissionUtils = new PermissionUtility(context,this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    /**
     * Method to check the availability of location permissions
     * */

    public void checkpermission()
    {
        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
    }

    private boolean isPermissionGranted() {
        return permissionGranted;
    }

    /**
     * Method to verify google play services on the device
     * */

    public boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(map_actvity,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                showToast("This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Method to display the location on UI
     * */

    public Location getLocation() {

        if (isPermissionGranted()) {
            try
            {

                lastKnownLocation = LocationServices.FusedLocationApi
                        .getLastLocation(googleApiClient);

                Log.e(TAG, "getLocation: LAST KNOWN LOCATION" + (lastKnownLocation==null));
                return lastKnownLocation;
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
        }

        return null;

    }

    /**
     * Method gets the lat and long and converts to address via google geocoder
     */
    public Address getAddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Method used to build GoogleApiClient
     */

    public void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) map_actvity)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) map_actvity)
                .addApi(LocationServices.API).build();

        googleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        lastKnownLocation = getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(map_actvity, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }

    /**
     * Method used to connect GoogleApiClient
     */
    public void connectApiClient()
    {
        googleApiClient.connect();
    }

    /**
     * Method used to get the GoogleApiClient
     */
    public GoogleApiClient getGoogleApiCLient()
    {
        return googleApiClient;
    }


    /**
     * Handles the permission results
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    /**
     * Handles the activity results
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        lastKnownLocation = getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    public void PermissionGranted(int request_code) {
        Log.d(TAG,"Permission granted");
        permissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.d(TAG,"Permission partially granted");

    }

    @Override
    public void PermissionDenied(int request_code)      {  Log.d(TAG,"Permission denied");

    }

    @Override
    public void NeverAskAgain(int request_code)
    {
        Log.d(TAG,"Permission Never Ask");
    }



    private void showToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }




}
