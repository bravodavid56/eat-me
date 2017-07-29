package com.example.bravodavid56.eatme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.bravodavid56.eatme.activity1.*;
import com.example.bravodavid56.eatme.activity2.*;
import com.example.bravodavid56.eatme.activity3.*;

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
    public void onRandomClick(View view) {
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
}
