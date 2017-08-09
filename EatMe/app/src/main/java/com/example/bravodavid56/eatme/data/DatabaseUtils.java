package com.example.bravodavid56.eatme.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by EVAN on 7/29/2017.
 */

public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase db)
    {
        Cursor cursor = db.query(
                Contract.TABLE_ITEMS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    public static Cursor getAllOrderBy(SQLiteDatabase db, String orderBy)
    {
        Cursor cursor = db.query(
                Contract.TABLE_ITEMS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                orderBy,
                null
        );
        return cursor;
    }

    public static Cursor getAllByPrice(SQLiteDatabase db, String price)
    {
        Cursor cursor = db.query(
                Contract.TABLE_ITEMS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                price,
                null
        );
        return cursor;
    }

    public static Cursor getAllOrderBySelection(SQLiteDatabase db, String place, String price, String rating)
    {

        Log.e(TAG, "getAllOrderBySelection: " + place + " " + price + " " + rating);
        String sqlStatement = "SELECT * FROM " + Contract.TABLE_ITEMS.TABLE_NAME + " WHERE " +
                "PRICE = ? AND RATING LIKE '"+rating+"%' AND CATEGORIES LIKE '%"+place+"%'" ;
        Cursor cursor = db.rawQuery(sqlStatement, new String[] {price}) ;
        Log.e(TAG, "getAllOrderBySelection: " + cursor.getCount() );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<BusinessItem> items)
    {
        db.beginTransaction();
        try{
            for (BusinessItem i: items)
            {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_ID, i.getId());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_NAME, i.getName());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_IMAGE_URL, i.getImage_url());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_URL, i.getUrl());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_DISPLAY_PHONE, i.getDisplay_phone());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_REVIEW_COUNT, i.getReview_count());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_ADDRESS, i.getAddress());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_RATING, i.getRating());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_CATEGORIES, i.getCategories());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_PRICE, i.getPrice());
                db.insert(Contract.TABLE_ITEMS.TABLE_NAME, null, cv);

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db){
        db.delete(Contract.TABLE_ITEMS.TABLE_NAME, null, null);
    }
}
