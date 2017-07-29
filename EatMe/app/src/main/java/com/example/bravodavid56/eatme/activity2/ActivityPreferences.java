package com.example.bravodavid56.eatme.activity2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bravodavid56.eatme.*;

/**
 * Created by bravodavid56 on 7/29/2017.
 */

public class ActivityPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        // the layout for this activity is under res->layout->activity_preferences.xml

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("You clicked Preferences");
    }
}
