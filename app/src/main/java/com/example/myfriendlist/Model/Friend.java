package com.example.myfriendlist.Model;

import java.io.Serializable;
import java.util.Date;

public class Friend implements Serializable {

    private int Id;
    private String Name;
    private String Address;
    private double Latitude;
    private double Longitude;
    private int PhoneNumber;
    private String EMail;
    private String Website;
    private String Birthday;
    private String ImgPath;



    public Friend(int id, String name, String address, double latitude, double longitude, int phoneNumber, String eMail, String webSite, String birthday, String imgPath) {
        Id = id;
        Name = name;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
        PhoneNumber = phoneNumber;
        EMail = eMail;
        Website = webSite;
        Birthday = birthday;
        ImgPath = imgPath;
    }



    public Friend(String name, String address, double latitude, double longitude, int phoneNumber, String eMail, String webSite, String birthday, String imgPath) {
        Name = name;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
        PhoneNumber = phoneNumber;
        EMail = eMail;
        Website = webSite;
        Birthday = birthday;
        ImgPath = imgPath;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }
}
