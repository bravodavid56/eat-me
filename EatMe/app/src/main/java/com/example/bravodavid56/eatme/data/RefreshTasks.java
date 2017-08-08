package com.example.bravodavid56.eatme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class RefreshTasks {
    public static void refreshArticles(Context context) {
        ArrayList<BusinessItem> result;
        // this will hold all of the NewsItems
        URL url = NetworkUtils.buildUrl("South Gate, CA");

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
