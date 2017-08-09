package com.example.bravodavid56.eatme.activity2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Location;
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
import com.example.bravodavid56.eatme.data.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        recycle = (RecyclerView) findViewById(R.id.recyclerViewPreferences);
        recycle.setLayoutManager(new LinearLayoutManager(this));

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


//        while (cursor.getCount() == 0) {
//            cursor.close();
//            cursor = DatabaseUtils.getAllOrderBySelection(db, placeEdit, price, rating);
//
//        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                placeEdit = edit.getText().toString();


                price = spinnerPrice.getSelectedItem().toString();

                int price2 = price.length();

                rating = spinnerRating.getSelectedItem().toString();


                db = new DBHelper(ActivityPreferences.this).getReadableDatabase();


                cursor = DatabaseUtils.getAllOrderBySelection(db, placeEdit, String.valueOf(price2), rating);



                LinearLayout preferencesPopup = (LinearLayout) findViewById(R.id.popupPreferences);
                preferencesPopup.setVisibility(View.INVISIBLE);

                Log.i("Place", placeEdit);
                Log.i("Price", price);
                Log.i("Rating", rating);

                adapter = new BusinessItemAdapter(cursor);
                recycle.setAdapter(adapter);
            }
        });
    }


    //Intent sendDataToFragment = new Intent(ActivityPreferences.this, FragmentPreferences.class);


//                db = new DBHelper(ActivityPreferences.this).getReadableDatabase();
//                cursor = DatabaseUtils.getAllByPrice(db,price);
//                adapter = new BusinessItemAdapter(cursor);


//                Bundle args = new Bundle();
//                args.putString("Place", placeEdit);
//                args.putString("Price", price);
//                args.putString("Rating", rating);
//
//
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentPreferences frag = new FragmentPreferences();
//                frag.setArguments(args);
//                frag.show(fm, "FragmentPreferences");


    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(ActivityPreferences.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new BusinessItemAdapter(cursor);
        recycle.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

}









//        textView.setText("You clicked Preferences");
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showToast("Proceed to the next step");
//
//
//                String label = "Restauraunt Title";
//                String uriBegin = "geo:" + latitude + "," + longitude;
//                String query = latitude + "," + longitude + "(" + label + ")";
//                String encodedQuery = Uri.encode(query);
//                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
//                Uri uri = Uri.parse(uriString);
//                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//                startActivity(mapIntent);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
//            }
//        });
//


















