package com.example.bravodavid56.eatme.activityRandom;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.bravodavid56.eatme.*;
import com.example.bravodavid56.eatme.data.LinearLayoutManagerWithSmoothScroller;

import java.util.Random;

import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;

import static java.lang.Thread.sleep;

/**
 * Created by bravodavid56 on 7/29/2017.
 */

public class ActivityRandom extends AppCompatActivity {


    public static final String TAG = "randomActivity";
    private RecyclerView recyclerView;
    private BusinessItemAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Button button;
    private MediaPlayer m;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        // replace the sound file that plays during roulette
        mp = MediaPlayer.create(this, R.raw.spinning);
        m = MediaPlayer.create(this, R.raw.winning);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
        db = new DBHelper(ActivityRandom.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);

        button = (Button) findViewById(R.id.button_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollingTest();
            }
        });

//        new TestApiCall().execute();
    }


    private void scrollingTest()
    {
        mp.start();
        button.setVisibility(View.GONE);
        Random random = new Random();
        int i = random.nextInt(cursor.getCount());
        recyclerView.smoothScrollToPosition(i);
        recyclerView.addOnScrollListener(new CustomScrollListener(i));
    }

    class CustomScrollListener extends RecyclerView.OnScrollListener{

        private int pos;

        public CustomScrollListener(int pos){
            super();
            this.pos = pos;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE)
            {
                recyclerView.scrollToPosition(pos);
                mp.pause();
                //mp = MediaPlayer.create(this, R.raw.winning);
                m.start();
            }
        }
    }

//    class TestApiCall extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                URL url = NetworkUtils.buildUrl("South Gate, CA");
//                String response = NetworkUtils.getResponse(url);
//                return response;
//
//                // prints the response just to show it's calling it correctly
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            TextView tx = (TextView) findViewById(R.id.textView);
//            tx.setText(s);
//            try {
//                NetworkUtils.parseSearchJson(s);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }

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
