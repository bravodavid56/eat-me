package com.example.bravodavid56.eatme.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    public static void bulkInsert(SQLiteDatabase db, ArrayList<BusinessItem> items)
    {
        db.beginTransaction();
        try{
            for (BusinessItem i: items)
            {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_NAME, i.getName());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_IMAGE_URL, i.getImage_url());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_URL, i.getUrl());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_DISPLAY_PHONE, i.getDisplay_phone());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_REVIEW_COUNT, i.getReview_count());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_RATING_IMG_URL, i.getRating_img_url());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_SNIPPET_TEXT, i.getSnippet_text());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_IMAGE_URL, i.getSnippet_image_url());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_ADDRESS, i.getAddress());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_MENU_PROVIDER, i.getMenu_provider());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_RATING, i.getRating());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_CATEGORIES, i.getCategories());
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
