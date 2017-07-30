package com.example.bravodavid56.eatme.activity1;


import android.media.Image;

// restaurant item pulled from the yelp api into the database. should contain things such as
// restaurant name, pictures, description, pricing, address, etc.
public class resItem {

    private String name;
    private Image image;
    private String address;

    public resItem(String name, Image image, String address)
    {
        this.name = name;
        this.image = image;
        this.address = address;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setImage(Image image) { this.image = image; }

    public Image getImage() { return image; }

    public void setAddress(String address) { this.address = address; }

    public String getAddress() { return address; }
}
