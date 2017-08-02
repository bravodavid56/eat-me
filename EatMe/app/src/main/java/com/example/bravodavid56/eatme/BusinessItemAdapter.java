package com.example.bravodavid56.eatme;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bravodavid56.eatme.data.BusinessItem;
import com.example.bravodavid56.eatme.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by EVAN on 7/29/2017.
 */

public class BusinessItemAdapter extends RecyclerView.Adapter<BusinessItemAdapter.BusinessItemViewHolder>{


    private Cursor cursor;
    private Context context;
    private static final String TAG = "businessItemAdapter";

    public BusinessItemAdapter(Cursor cursor)
    {
        this.cursor = cursor;
    }

    @Override
    public BusinessItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int list_item_id = R.layout.business_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(list_item_id, parent, false);
        return new BusinessItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusinessItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class BusinessItemViewHolder extends RecyclerView.ViewHolder{

        public final ImageView iv_business_image;
        public final TextView tv_business_name;
        public final TextView tv_business_address;
        public final TextView tv_business_phone;
        public final TextView tv_business_url;
        public final TextView tv_business_categories;
        public final TextView tv_business_review_count;
        public final TextView tv_business_rating;

        public BusinessItemViewHolder(View view)
        {
            super(view);
            iv_business_image = (ImageView) view.findViewById(R.id.business_image);
            tv_business_name = (TextView) view.findViewById(R.id.business_name);
            tv_business_address = (TextView) view.findViewById(R.id.business_address);
            tv_business_phone = (TextView) view.findViewById(R.id.business_phone);
            tv_business_url = (TextView) view.findViewById(R.id.business_url);
            tv_business_categories = (TextView) view.findViewById(R.id.business_categories);
            tv_business_review_count = (TextView) view.findViewById(R.id.business_review_count);
            tv_business_rating = (TextView) view.findViewById(R.id.business_rating);
        }

        public void bind(int index)
        {
            cursor.moveToPosition(index);
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_IMAGE_URL));

            if (url != null)
            {
                Picasso.with(context)
                        .load(url)
                        .into(iv_business_image);
            }

            tv_business_name.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_NAME)));
            tv_business_address.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_ADDRESS)));
            tv_business_phone.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_DISPLAY_PHONE)));
            tv_business_url.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_URL)));
            tv_business_categories.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_CATEGORIES)));
            tv_business_review_count.setText(cursor.getInt(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_REVIEW_COUNT)));
            double rating = cursor.getDouble(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_RATING));
            String ratingString = Double.toString(rating);
            tv_business_rating.setText(ratingString);
        }
    }
}
