package com.example.bravodavid56.eatme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.example.bravodavid56.eatme.activityRandom.*;
import com.example.bravodavid56.eatme.activity2.*;
import com.example.bravodavid56.eatme.activity3.*;


import com.example.bravodavid56.eatme.data.LocationHelper;
import com.example.bravodavid56.eatme.multitouchrandomizer.Multitouch;

import com.example.bravodavid56.eatme.data.RefreshTasks;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.MalformedURLException;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private String TAG = "EatMe";
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private LocationHelper locationHelper;
    private boolean connected = false;
    private Address mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isFirst", true);

        if (isFirst) {
            Log.e(TAG, "onCreate: WAS FIRST" );
            startLoading();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        if (locationHelper.checkPlayServices()) {
            locationHelper.buildGoogleApiClient();

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
        if (mAddress != null) {
            startLoading();
        }
    }

    // the different methods correspond to the different buttons on the main activity
    // each method starts a new activity
    // each activity is in its own package,
    public void onRandomClick(View view) throws MalformedURLException {
        Log.e(TAG, "onRandomClick: " );
        Intent intent = new Intent(this, ActivityRandom.class);
        startActivity(intent);

    }

    public void onPrefClick(View view) {
        Log.e(TAG, "onPrefClick: ");
        Intent intent = new Intent(this, ActivityPreferences.class);
        startActivity(intent);
    }

    public void onNearbyClick(View view) {
        Log.e(TAG, "onNearbyClick: ");
        Intent intent = new Intent(this, ActivityNearby.class);
        startActivity(intent);
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                // maybe a progress bar, loading screen, etc...
            }

            @Override
            public Void loadInBackground() {
                if (mAddress != null) {
                    Log.e(TAG, "loadInBackground: " + mAddress.getAddressLine(0) );
                    RefreshTasks.refreshArticles(MainActivity.this, mAddress);
                }
                // RefreshTasks.refreshArticles(MainActivity.this);

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
    // this async task is just to test the API calls are being made correctly
    // we can replace this later

    public void startLoading() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(1, null, this).forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.connect) {

            Intent intent = new Intent(this, Multitouch.class);
            startActivity(intent);

        }
        return true;
    }


    // THIS IS THE LOCATION GETTER STUFF
    // IF ANYTHING BREAKS, IT'LL BE HERE


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {
            Log.e(TAG, "onConnected: "+mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
            runAddress();
            startLoading();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "onConnected: CONNECTED" );
        connected = true;
        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {
            Log.e(TAG, "onConnected: "+mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
            runAddress();
            startLoading();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended: " );
        locationHelper.connectApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: " );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void runAddress() {
        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                getAddress();
        } else {

        }

    }
    public void getAddress() {
        Address locationAddress;
        locationAddress = locationHelper.getAddress(latitude, longitude);
        if (locationAddress != null) {
            Log.e(TAG, "getAddress: Location Address:" + locationAddress.getAddressLine(0));
            mAddress = locationAddress;
        }
    }



}