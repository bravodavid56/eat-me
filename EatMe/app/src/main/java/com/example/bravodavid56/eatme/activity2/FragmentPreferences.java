package com.example.bravodavid56.eatme.activity2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import com.example.bravodavid56.eatme.BusinessItemAdapter;
import com.example.bravodavid56.eatme.R;
import com.example.bravodavid56.eatme.data.DBHelper;
import com.example.bravodavid56.eatme.data.DatabaseUtils;

import org.w3c.dom.Text;

/**
 * Created by angel on 8/4/17.
 */

public class FragmentPreferences extends DialogFragment {



    private Button add;
    private ImageView business_image;
    private TextView  business_name;
    private TextView  business_rating;
    private TextView  business_address;
    private TextView  business_number;
    private TextView business_price;



    private final String TAG = "fragment";

    public FragmentPreferences() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_list_item, container, false);


        business_image = (ImageView) view.findViewById(R.id.business_image);
        business_name = (TextView) view.findViewById(R.id.business_name);
        business_address = (TextView) view.findViewById(R.id.business_address);
        business_number = (TextView) view.findViewById(R.id.business_phone);
        business_rating = (TextView) view.findViewById(R.id.business_rating);
        business_price = (TextView) view.findViewById(R.id.business_price);

        Bundle args = getArguments();
        String place = args.getString("Place");
        String price = args.getString("Price");
        String rating = args.getString("Rating");



<<<<<<< Updated upstream
        business_name.setText("Destination: " + place);
        business_price.setText("Price:" + price);
        business_rating.setText("Price:" + rating);
=======



            business_name.setText("Destination: " + place);
            business_price.setText("Price:" + price);
            business_rating.setText("Price:" + rating);
>>>>>>> Stashed changes





        add = (Button) view.findViewById(R.id.fragButton);






        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something on click
            }
        });

        return view;
    }
}
