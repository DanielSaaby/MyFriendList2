package com.example.myfriendlist;

import android.content.Context;

import com.example.myfriendlist.Interface.IDataAccess;

public class DataAccessFactory {

    public static IDataAccess getInstance(Context c)
    { return new SQLiteImpl(c); }
}
