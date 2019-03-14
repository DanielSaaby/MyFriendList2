package com.example.myfriendlist.Model;

import java.util.ArrayList;

public class ListOfFriends {

    ArrayList<Friend> friendList;

    public ListOfFriends()
    {
        friendList = new ArrayList<Friend>();
        friendList.add(new Friend("Jimmi Kristensen", 27));
        friendList.add(new Friend("Jesper Jacobsen", 27));
        friendList.add(new Friend("Lars Schmidt", 24));
        friendList.add(new Friend("Mads Huus", 25));
        friendList.add(new Friend("Jonas Jacobsen", 25));
        friendList.add(new Friend("Jesper Lund", 27));
        friendList.add(new Friend("Nicolai Frandsen", 27));
        friendList.add(new Friend("Simon Leth", 27));
        friendList.add(new Friend("Nicklas Pus ", 26));
        friendList.add(new Friend("Søren Vormisto ", 25));
        friendList.add(new Friend("Frederik Tubæk", 21));
        friendList.add(new Friend("Kent Pedersen", 29));

    }

    public ArrayList<Friend> getAll()
    {
        return friendList;
    }

    public String[] getNames()
    {
        String[] res = new String[friendList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = friendList.get(i).getName();
        }
        return res;
    }
}
