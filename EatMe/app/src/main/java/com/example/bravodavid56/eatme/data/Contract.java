package com.example.bravodavid56.eatme.data;

import android.provider.BaseColumns;

/**
 * Created by EVAN on 7/29/2017.
 */

public class Contract {

    public static class TABLE_ITEMS implements BaseColumns {

        public static final String TABLE_NAME = "businesses";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_DISPLAY_PHONE = "display_phone";
        public static final String COLUMN_NAME_REVIEW_COUNT = "review_count";
        public static final String COLUMN_NAME_RATING_IMG_URL = "rating_img_url";
        public static final String COLUMN_NAME_SNIPPET_TEXT = "snippet_text";
        public static final String COLUMN_NAME_SNIPPET_IMAGE_URL = "snippet_image_url";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_MENU_PROVIDER = "menu_provider";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_CATEGORIES = "categories";

    }
}
