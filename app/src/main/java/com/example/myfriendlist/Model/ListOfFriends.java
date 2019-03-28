package com.example.myfriendlist.Model;

import java.util.ArrayList;

public class ListOfFriends {

    ArrayList<Friend> friendList;

    public ListOfFriends()
    {
        friendList = new ArrayList<Friend>();
        friendList.add(new Friend(1,"Jimmi Kristensen", "Aakjærsevej 11, 6920 Videbæk", 55.992248, 8.742392, 53834542, "jimmi@live.dk", "https://www.facebook.com/jimmi.kristensen.98", "26011992", "" ));
        friendList.add(new Friend(2,"Jesper Jacobsen", "Buen 1C, 6920 Videbæk", 55.990255, 8.743010, 42560212, "jesperj@live.dk", "https://www.facebook.com/jesper.jacobsen.7315", "021291", "" ));
        friendList.add(new Friend(3,"Lars Schmidt", "Frodesgade 58, 6700 Esbjerg", 55.473689, 8.436949, 53834542, "lars@live.dk", "https://www.facebook.com/xschmidt1", "251094", "" ));
        friendList.add(new Friend(4,"Mads Huus", "Hvirveltoften 11, 6920 Videbæk", 55.994820, 8.736627, 21251164, "mads@live.dk", "https://www.facebook.com/MHuusG", "021193", "" ));
        friendList.add(new Friend(5,"Jonas Jacobsen", "Østergade 20, 6920 Videbæk", 55.992228, 8.746349, 53834542, "jonas@live.dk", "https://www.facebook.com/jonas.b.jacobsen.3", "040694", "" ));
        friendList.add(new Friend(6,"Jesper Lund", "Dalgasgade 31, 6920 Videbæk", 55.991807, 8.741780, 53834542, "jesperl@live.dk", "https://www.facebook.com/jesper.lund.39", "080692", "" ));
        friendList.add(new Friend(7,"Nicolai Frandsen", "Hack kampmanns Pl. 6, 8000 Aarhus", 56.153392, 10.213583, 53834542, "nicolai@live.dk", "https://www.facebook.com/nicolai.t.frandsen", "031191", "" ));
        friendList.add(new Friend(8,"Simon Leth", "Skolegade 12, 7400 Herning", 56.153392, 10.213583, 53834542, "simon@live.dk", "https://www.facebook.com/simon.l.larsen", "250792", "" ));
        friendList.add(new Friend(9,"Nicklas Søndergaard ", "Buen 1B, 6920 Videbæk", 55.990322, 8.743119, 28351517, "nicklas@live.dk", "https://www.facebook.com/nicklas.sondergaard?ref=br_rs", "190193", "" ));
        friendList.add(new Friend(10,"Søren Vormisto ", "Kongevejen 37, 7430 Ikast", 56.136608, 9.152281, 53834542, "soren@live.dk", "https://www.facebook.com/vormistox", "040294", "" ));
        friendList.add(new Friend(11,"Frederik Tubæk", "Englandsgade 35, 6700 Esbjerg",  55.467182, 8.455123, 50984278, "frederik@live.dk", "https://www.facebook.com/profile.php?id=1192789766", "150997", "" ));
        friendList.add(new Friend(12,"Kent Pedersen", "Skolegade 60, 6700 Esbjerg", 55.467311, 8.447195, 28262549, "kent@live.dk", "https://www.facebook.com/KentJuulp", "250889", "" ));
        friendList.add(new Friend(13,"Daniel Rasmussen", "Engdraget 4, 6800 Varde", 55.616477, 8.483755, 26294128, "daniel@live.dk", "https://www.facebook.com/daniel.rasmussen.1800", "100892", "" ));
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
