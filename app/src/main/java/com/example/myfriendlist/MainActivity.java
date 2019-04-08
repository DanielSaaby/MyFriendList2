package com.example.myfriendlist;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static String TAG = "Friend2";

    private FriendAdapter fa;
    private IDataAccess mDateAccess;

    private ListView friendList;


    ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("MyFriends");

        friendList = findViewById(R.id.friendList);

        checkPermissions();


        mDateAccess = DataAccessFactory.getInstance(this);
        setupListView();



        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
                Intent x = new Intent(MainActivity.this, DetailActivity.class);
                Friend friend = friends.get(position);
                x.putExtra("friend", friend);
                startActivity(x);
            }
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

    private void setupListView() {
        friends = mDateAccess.selectAll();

        fa = new FriendAdapter(this, R.layout.activity_main_cell, friends);
        friendList.setAdapter(fa);
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


    @Override
    protected void onRestart() {
        super.onRestart();
        setupListView();
    }


    /**
     * Checks if the app has the required permissions, and prompts the user with the ones missing.
     */
    private void checkPermissions() {
        ArrayList<String> permissions = new ArrayList<String>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.CAMERA);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.CALL_PHONE);


        if (permissions.size() > 0)
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 1);
    }

}



