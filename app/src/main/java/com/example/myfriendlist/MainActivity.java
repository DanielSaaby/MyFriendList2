package com.example.myfriendlist;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myfriendlist.Model.Friend;
import com.example.myfriendlist.Model.ListOfFriends;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";



    ListOfFriends listOfFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle("MyFriends");
        listOfFriends = new ListOfFriends();

        String[] friends;

        friends = listOfFriends.getNames();

        ListAdapter adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        friends);

        setListAdapter(adapter);




    }

    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(MainActivity.this, DetailActivity.class);
        Log.d(TAG, "Detail activity will be started");
        Friend friend = listOfFriends.getAll().get(position);
        addData(x, friend);
        startActivity(x);
        Log.d(TAG, "Detail activity is started");

    }

    private void addData(Intent x, Friend f)
    {
        x.putExtra("friend", f);
    }



}



