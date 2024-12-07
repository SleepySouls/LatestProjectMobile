package com.example.doan;

public class potholeNearList {
    private int imageStreet;
    private String streetName;
    private int imageDistance;
    private String DistanceIn;
    private String Distance;
    public potholeNearList(int imageStreet, String streetName, int imageDistance, String DistanceIn, String Distance) {
        this.imageStreet = imageStreet;
        this.streetName = streetName;
        this.imageDistance = imageDistance;
        this.DistanceIn = DistanceIn;
        this.Distance = Distance;
    }
    public int getImageStreet() {
        return imageStreet;
    }
    public void setImageStreet(int imageStreet){this.imageStreet = imageStreet;}
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(){this.streetName = streetName; }
    public int getImageDistance() {
        return imageDistance;
    }
    public void setImageDistance(int imageDistance){this.imageDistance = imageDistance;}
    public String getDistanceIn() {
        return DistanceIn;
    }
    public void setDistanceIn(){this.DistanceIn = DistanceIn; }
    public String getDistance() {
        return Distance;
    }
    public void setDistance(){this.Distance = DistanceIn; }
}