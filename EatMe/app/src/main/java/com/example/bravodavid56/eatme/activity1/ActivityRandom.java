package com.example.bravodavid56.eatme.activity1;

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
