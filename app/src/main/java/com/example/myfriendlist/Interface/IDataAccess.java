package com.example.myfriendlist.Interface;

import com.example.myfriendlist.Model.Friend;

import java.util.ArrayList;
import java.util.List;

public interface IDataAccess {

    long insert(Friend p);

    void deleteAll();

    void deleteById(int id);

    ArrayList<Friend> selectAll();

    void update(Friend p);
}
