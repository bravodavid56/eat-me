package com.example.bravodavid56.eatme.activityRandom;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;

import com.example.bravodavid56.eatme.*;
import com.example.bravodavid56.eatme.data.Contract;
import com.example.bravodavid56.eatme.data.LinearLayoutManagerWithSmoothScroller;

import java.util.Random;

import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;

import static java.lang.Thread.sleep;



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

        while (cursor.getCount() == 0)
        {
            cursor.close();
            cursor = DatabaseUtils.getAll(db);
        }


        adapter = new BusinessItemAdapter(cursor);
        recyclerView.setAdapter(adapter);

        button = (Button) findViewById(R.id.button_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToRandom();
                LinearLayout lay = (LinearLayout) findViewById(R.id.popup);
                lay.setVisibility(View.INVISIBLE);
            }
        });
//       // new TestApiCall().execute();
    }

    public void googleMapsDirections(View view)
    {
        String address = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_ADDRESS));
        address = address.replace(' ', '+');
        address = address.replace(",", "");
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + address));
        Log.d(TAG, address);
        startActivity(intent);
    }

    private void scrollToRandom()
    {
        button.setVisibility(View.GONE);
        Random random = new Random();
        int count = cursor.getCount();
        int i = random.nextInt(count);
        Log.d(TAG, "random value: " + Integer.toString(i));
        if (i == 0)
        {
            m.start();
        }
        else{
            mp.start();
            recyclerView.smoothScrollToPosition(i);
            recyclerView.addOnScrollListener(new CustomScrollListener(i));
        }

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