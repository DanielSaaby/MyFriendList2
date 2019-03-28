package com.example.myfriendlist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfriendlist.Interface.IDataAccess;
import com.example.myfriendlist.Model.Friend;
import com.example.myfriendlist.Model.ListOfFriends;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static String TAG = "Friend2";

    private FriendAdapter fa;
    private IDataAccess mDateAccess;


    ListOfFriends listOfFriends;
    ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView friendList = this.findViewById(R.id.friendList);
        mDateAccess = DataAccessFactory.getInstance(this);

        this.setTitle("MyFriends");
        listOfFriends = new ListOfFriends();
        friends = mDateAccess.selectAll();


        fa = new FriendAdapter(this, R.layout.activity_main_cell, friends);
        friendList.setAdapter(fa);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
                Intent x = new Intent(MainActivity.this, DetailActivity.class);
                Log.d(TAG, "Detail activity will be started");
                Friend friend = listOfFriends.getAll().get(position);
                addData(x, friend);
                startActivity(x);
                Log.d(TAG, "Detail activity is started");            }
        });


        Button btnAddFriend = findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(x);
            }
        });



    }

    private class FriendAdapter extends ArrayAdapter<Friend> {

        List<Friend> friendsList = new ArrayList<>();


        public FriendAdapter(Context context, int activity_main_cell, ArrayList<Friend> friends) {
            super(context, activity_main_cell, friends);
            this.friendsList.addAll(friends);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {


            Friend friend = friendsList.get(position);

            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.activity_main_cell, null);
                Log.d("LIST", "Position: " + position + " View created");
            }
            else {
                Log.d("LIST", "Position: " + position + " View Reused");
            }


            TextView txtName = v.findViewById(R.id.txtName);
            TextView txtAddress = v.findViewById(R.id.txtAddress);

            ImageView thumbnail = v.findViewById(R.id.imgThumnail);



            txtName.setText(friend.getName());
            txtAddress.setText(friend.getAddress());

            if(!friend.getImgPath().isEmpty() && !friend.getImgPath().equals("No Path"))
            {
                thumbnail.setImageURI(Uri.fromFile(new File(friend.getImgPath())));
            } else
            {
                thumbnail.setImageResource(R.drawable.placeholder);
            }


            return v;
        }



    }

    private void addData(Intent x, Friend f)
    {
        x.putExtra("friend", f);
    }







}



