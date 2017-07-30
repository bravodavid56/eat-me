package com.example.bravodavid56.eatme.data;

import java.util.List;

/**
 * Created by EVAN on 7/29/2017.
 */

public class BusinessItem {

    private String name;
    private String image_url;
    private String url;
    private String display_phone;
    private int review_count;
    private String rating_img_url;
    private String snippet_text;
    private String snippet_image_url;
    private String address; // the data type from the json is initially a list, but we can extract the address string
    private String menu_provider;
    private int rating;

    public BusinessItem(String name, String image_url, String url, String display_phone, int review_count, String rating_img_url, String snippet_text, String snippet_image_url, String address, String menu_provider, int rating) {
        this.name = name;
        this.image_url = image_url;
        this.url = url;
        this.display_phone = display_phone;
        this.review_count = review_count;
        this.rating_img_url = rating_img_url;
        this.snippet_text = snippet_text;
        this.snippet_image_url = snippet_image_url;
        this.address = address;
        this.menu_provider = menu_provider;
        this.rating = rating;
    }

    public BusinessItem()
    {

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

    public String getRating_img_url() {
        return rating_img_url;
    }

    public void setRating_img_url(String rating_img_url) {
        this.rating_img_url = rating_img_url;
    }

    public String getSnippet_text() {
        return snippet_text;
    }

    public void setSnippet_text(String snippet_text) {
        this.snippet_text = snippet_text;
    }

    public String getSnippet_image_url() {
        return snippet_image_url;
    }

    public void setSnippet_image_url(String snippet_image_url) {
        this.snippet_image_url = snippet_image_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMenu_provider() {
        return menu_provider;
    }

    public void setMenu_provider(String menu_provider) {
        this.menu_provider = menu_provider;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
