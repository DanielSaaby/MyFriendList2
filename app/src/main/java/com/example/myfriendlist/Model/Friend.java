package com.example.myfriendlist.Model;

import java.io.Serializable;

public class Friend implements Serializable {

    private String Name;
    private int Age;
    private String ImgPath;
    private double Latitude;
    private double Longitude;


    public Friend(String name, int age, double latitude, double longitude) {
        Name = name;
        Age = age;
        Latitude = latitude;
        Longitude = longitude;

    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return Age;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        this.ImgPath = imgPath;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAge(int age) {
        Age = age;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }
}
