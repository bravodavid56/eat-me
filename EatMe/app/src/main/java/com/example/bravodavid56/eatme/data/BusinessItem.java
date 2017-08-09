package com.example.bravodavid56.eatme.data;

import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by EVAN on 7/29/2017.
 */

public class BusinessItem {

    private String id;
    private String name;
    private String image_url;
    private String url;
    private String display_phone;
    private int review_count;
    private String address; // the data type from the json is initially a list, but we can extract the address string
    private double rating;
    private String categories; // need to create a method to convert the list to string and string to list
    private String price;
    private String Image;

    public BusinessItem(String id, String name, String image_url, String url, String display_phone, int review_count, String address, double rating, String categories, String price) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.url = url;
        this.display_phone = display_phone;
        this.review_count = review_count;
        this.address = address;
        this.rating = rating;
        this.categories = categories;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public BusinessItem()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay_phone() {
        return display_phone;
    }

    public void setDisplay_phone(String display_phone) {
        this.display_phone = display_phone;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setImage() { this.Image = Image; }

    public String getImage() { return this.Image; }

}
