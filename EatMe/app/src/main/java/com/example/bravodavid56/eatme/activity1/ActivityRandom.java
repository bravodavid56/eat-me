package com.example.bravodavid56.eatme.activity1;


import android.net.Network;
import android.os.AsyncTask;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bravodavid56.eatme.*;
import com.example.bravodavid56.eatme.data.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;

/**
 * Created by bravodavid56 on 7/29/2017.
 */

public class ActivityRandom extends AppCompatActivity {


    public static final String TAG = "randomActivity";
    private RecyclerView recyclerView;
    private BusinessItemAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new DBHelper(ActivityRandom.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);

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

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(ActivityRandom.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }


}
