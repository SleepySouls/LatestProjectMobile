package com.example.doan;
public class helpfulnessList {
    private int imageAvatar;
    private String nameAcc;
    private int imageStreet;
    private String streetName;
    public helpfulnessList(int imageAvatar,String nameAcc,int imageStreet,String streetName)
    {
        this.imageAvatar= imageAvatar;
        this.imageStreet= imageStreet;
        this.nameAcc = nameAcc;
        this.streetName =streetName;
    }
    public int getImageStreet(){return imageStreet;}
    public int getImageAvatar(){return imageAvatar;}
    public String getNameAcc(){return nameAcc;}
    public String getStreetName(){return streetName;}
    public void setImageAvatar(int imageAvatar){this.imageAvatar = imageAvatar;}
    public void setImageStreet(int imageStreet){this.streetName= streetName;}
    public void setStreetName(String streetName){this.streetName=streetName;}
    public void setNameAcc(String nameAcc){this.nameAcc=nameAcc;}

}
