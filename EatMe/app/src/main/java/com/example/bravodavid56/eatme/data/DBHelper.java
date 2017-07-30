package com.example.bravodavid56.eatme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by EVAN on 7/29/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "businesses.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_ITEMS.TABLE_NAME + " (" +
                Contract.TABLE_ITEMS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_IMAGE_URL + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_URL + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_DISPLAY_PHONE + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_REVIEW_COUNT + " INTEGER, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_RATING_IMG_URL + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_SNIPPET_TEXT + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_SNIPPET_IMAGE_URL + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_ADDRESS + " TEXT, " + // there might be an issue with the data type of address. api says its a list not a string
                Contract.TABLE_ITEMS.COLUMN_NAME_MENU_PROVIDER + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_RATING + " INTEGER, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_CATEGORIES + " TEXT" +
                "); ";

        Log.d(TAG, "Create SQL table: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            db.execSQL("DROP TABLE " + Contract.TABLE_ITEMS.TABLE_NAME + " IF EXISTS;");
    }
}
