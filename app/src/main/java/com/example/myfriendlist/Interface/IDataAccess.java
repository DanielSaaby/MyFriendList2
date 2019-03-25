package com.example.myfriendlist.Interface;

import com.example.myfriendlist.Model.Friend;

import java.util.List;

public interface IDataAccess {

    long create(Friend friend);

    void deleteById();

    List<Friend> readAll();

    void update(Friend friend);
}
