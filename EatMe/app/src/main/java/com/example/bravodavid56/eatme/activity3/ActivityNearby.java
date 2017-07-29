package com.example.bravodavid56.eatme.activity3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bravodavid56.eatme.R;

/**
 * Created by bravodavid56 on 7/29/2017.
 */

public class ActivityNearby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        // the layout for this activity is under res->layout->activity_nearby.xml

        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText("You clicked Nearby");
    }
}
