package com.example.bravodavid56.eatme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bravodavid56.eatme.activityRandom.*;
import com.example.bravodavid56.eatme.activity2.*;
import com.example.bravodavid56.eatme.activity3.*;
import com.example.bravodavid56.eatme.data.RefreshTasks;

import java.net.MalformedURLException;

// just checking to see if im branching correctly

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void> {
    private String TAG = "EatMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isFirst", true);

        startLoading();

        if (isFirst) {
            Log.e(TAG, "onCreate: WAS FIRST" );
            startLoading();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
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

                RefreshTasks.refreshArticles(MainActivity.this);
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


}