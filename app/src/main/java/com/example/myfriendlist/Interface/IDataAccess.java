package com.example.myfriendlist.Interface;

import com.example.myfriendlist.Model.Friend;

import java.util.ArrayList;
import java.util.List;

public interface IDataAccess {


    /**
     * Adds a friend to the local database
     * @param friend
     * @return
     */
    long insert(Friend friend);

    /**
     * Deletes all friend-objects in the database
     */
    void deleteAll();

    /**
     * Deletes a given friend object from the database based on ID
     * @param id
     */
    void deleteById(int id);

    /**
     * Returns a list of all Friend-objects in the database
     * @return
     */
    ArrayList<Friend> selectAll();


    /**
     * Updates all the values of the new friend objects
     * @param p
     */
    void update(Friend p);
}
