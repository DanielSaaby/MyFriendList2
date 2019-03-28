package com.example.myfriendlist.Model;

import java.util.ArrayList;

public class ListOfFriends {

    ArrayList<Friend> friendList;

    public ListOfFriends()
    {
        friendList = new ArrayList<Friend>();
        friendList.add(new Friend("Jimmi Kristensen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Jesper Jacobsen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Lars Schmidt", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Mads Huus", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Jonas Jacobsen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Jesper Lund", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Nicolai Frandsen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Simon Leth", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Nicklas Pus ", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Søren Vormisto ", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Frederik Tubæk", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Kent Pedersen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend("Daniel Rasmussen", "Aakjærsevej 11", 55.46506827982128, 8.707485440852793, 53834542, "jimmi.kk@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));)
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
