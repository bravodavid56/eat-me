package com.example.bravodavid56.eatme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bravodavid56.eatme.activity1.*;
import com.example.bravodavid56.eatme.activity2.*;
import com.example.bravodavid56.eatme.activity3.*;
import com.example.bravodavid56.eatme.data.NetworkUtils;
import com.example.bravodavid56.eatme.*;

import java.net.MalformedURLException;
import java.net.URL;

// just checking to see if im branching correctly 

public class MainActivity extends AppCompatActivity {
    private String TAG = "EatMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // the different methods correspond to the different buttons on the main activity
    // each method starts a new activity
    // each activity is in its own package,
    public void onRandomClick(View view) throws MalformedURLException {
        Log.e(TAG, "onRandomClick: " );
        Intent intent = new Intent(this, ActivityRandom.class);
        startActivity(intent);
        new TestApiCall().execute();
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

    // this async task is just to test the API calls are being made correctly
    // we can replace this later
    class TestApiCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = NetworkUtils.buildUrl("San Francisco");
                String response = NetworkUtils.getResponse(url);
                return response;

                // prints the response just to show it's calling it correctly

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
