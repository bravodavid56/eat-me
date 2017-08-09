package com.example.bravodavid56.eatme.activity2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bravodavid56.eatme.BusinessItemAdapter;
import com.example.bravodavid56.eatme.R;
import com.example.bravodavid56.eatme.activityRandom.ActivityRandom;
import com.example.bravodavid56.eatme.data.Contract;
import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;
import com.example.bravodavid56.eatme.data.LinearLayoutManagerWithSmoothScroller;
import com.example.bravodavid56.eatme.data.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by bravodavid56 on 7/29/2017.
 * Updated by Angel on 8/2/2017
 */


public class ActivityPreferences extends AppCompatActivity {

    private String TAG = "preferencesTag";

    private Button startButton;
    private EditText edit;

    private Spinner spinnerPrice;
    private Spinner spinnerRating;


    private BusinessItemAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private RecyclerView recycle;

    private String placeEdit;
    private String price;
    private String rating;

    private MediaPlayer m;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        mp = MediaPlayer.create(this, R.raw.spinning);
        m = MediaPlayer.create(this, R.raw.winning);

        recycle = (RecyclerView) findViewById(R.id.recyclerViewFilter);
        recycle.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));

        startButton = (Button) findViewById(R.id.enterButton);


        spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);
        edit = (EditText) findViewById(R.id.select_place);




        final ArrayAdapter<CharSequence> spinnerPriceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_range_array, android.R.layout.simple_spinner_item);
        spinnerPriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(spinnerPriceAdapter);

        final ArrayAdapter<CharSequence> spinnerRatingAdapter = ArrayAdapter.createFromResource(this,
                R.array.rating_range_array, android.R.layout.simple_spinner_item);
        spinnerRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(spinnerRatingAdapter);




        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                placeEdit = edit.getText().toString();

                price = spinnerPrice.getSelectedItem().toString();
                int price2 = price.length();
                rating = spinnerRating.getSelectedItem().toString();

                db = new DBHelper(ActivityPreferences.this).getReadableDatabase();

                cursor = DatabaseUtils.getAllOrderBySelection(db, placeEdit, String.valueOf(price2), rating);
                if (cursor.getCount() == 0) {
                    Toast.makeText(ActivityPreferences.this, "No results found. Try again.", Toast.LENGTH_SHORT).show();
                } else {
                    LinearLayout preferencesPopup = (LinearLayout) findViewById(R.id.popupPreferences);
                    preferencesPopup.setVisibility(View.INVISIBLE);
                    adapter = new BusinessItemAdapter(cursor);
                    adapter.notifyDataSetChanged();
                    recycle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    scrollToRandom();
                }


            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(ActivityPreferences.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        //adapter = new BusinessItemAdapter(cursor);
        //recycle.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
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

            recycle.smoothScrollToPosition(i);
            recycle.addOnScrollListener(new ActivityPreferences.CustomScrollListener(i));
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
}
























