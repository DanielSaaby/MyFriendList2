package com.example.myfriendlist;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {

    IViewCallBack m_view;
    int count = 0;


    public MyLocationListener(IViewCallBack view) {
        this.m_view = view;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("TAG", "I CHANGED");
        m_view.setSpeed(location.getSpeed());
        m_view.setCounter(++count);
        m_view.setCurrentLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
