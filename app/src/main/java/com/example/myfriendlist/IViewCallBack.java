package com.example.myfriendlist;

import android.location.Location;

public interface IViewCallBack {

    void setSpeed(double speed);
    void setCurrentLocation(Location location);
    void setCounter(int conut);

}
