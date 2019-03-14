package com.example.myfriendlist.Model;

import java.io.Serializable;

public class Friend implements Serializable {

    private String Name;
    private int Age;
    private String ImgPath;


    public Friend(String name, int age) {
        Name = name;
        Age = age;

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
}
