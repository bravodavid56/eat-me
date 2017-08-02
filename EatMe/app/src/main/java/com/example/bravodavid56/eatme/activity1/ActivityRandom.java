package com.example.bravodavid56.eatme.activity1;

import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bravodavid56.eatme.*;
import com.example.bravodavid56.eatme.data.NetworkUtils;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by bravodavid56 on 7/29/2017.
 */

public class ActivityRandom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        // the layout for this activity is under res->layout->activity_random.xml
        new TestApiCall().execute();
    }

    class TestApiCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = NetworkUtils.buildUrl("South Gate, CA");
                String response = NetworkUtils.getResponse(url);
                return response;

                // prints the response just to show it's calling it correctly

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView tx = (TextView) findViewById(R.id.textView);
            tx.setText(s);
            try {
                NetworkUtils.parseSearchJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
