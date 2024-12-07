package com.example.doan;

public class streetlist {
    private int image;
    private String streetName;
    public streetlist(int image, String streetName) {
        this.image = image;
        this.streetName = streetName;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image){this.image = image;}
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(){this.streetName = streetName; }
}


