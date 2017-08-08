package com.example.bravodavid56.eatme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class RefreshTasks {
    private static final String TAG = "RefreshTasks";

    public static void refreshArticles(Context context, Address address) {
        ArrayList<BusinessItem> result;
        // this will hold all of the NewsItems
        URL url = NetworkUtils.buildUrl(address.getAddressLine(0) + " " + address.getAddressLine(1));
        Log.e(TAG, "refreshArticles: " );
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            // dump all the previous data from the database
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponse(url);
            // recieves a JSON response from the URL
            result = NetworkUtils.parseSearchJson(json);
            // insert all of the data from the network call into the database
            DatabaseUtils.bulkInsert(db, result);
            Log.e("RefreshTasks", "refreshArticles:1  " );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

}
