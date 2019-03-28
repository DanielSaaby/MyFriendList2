package com.example.myfriendlist;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfriendlist.Interface.IDataAccess;
import com.example.myfriendlist.Model.Friend;

public class AddFriendActivity extends AppCompatActivity {

    private IDataAccess mDateAccess;
    private LocationManager locManager;


    EditText InputName;
    EditText InputAddress;
    EditText InputLatitude;
    EditText InputLongitude;
    EditText InputPhonenumber;
    EditText InputEmail;
    EditText InputWebsite;
    EditText InputBirthdate;


    String Name;
    String Address;
    double Latitude;
    double Longitude;
    int Phonenumber;
    String Email;
    String Website;
    String Birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);


        InputName = findViewById(R.id.inpName);
        InputAddress = findViewById(R.id.inpAddress);
        InputLatitude = findViewById(R.id.inpLatitude);
        InputLongitude = findViewById(R.id.inpLongitude);
        InputPhonenumber = findViewById(R.id.inpPhonenumber);
        InputEmail = findViewById(R.id.inpEmail);
        InputWebsite = findViewById(R.id.inpWebsite);
        InputBirthdate = findViewById(R.id.inpBirthdate);

        mDateAccess = DataAccessFactory.getInstance(this);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Name = InputName.getText().toString().trim();
                Address = InputAddress.getText().toString().trim();
                if(!isNullOrEmpty(InputLatitude.getText().toString()) && !isNullOrEmpty(InputLongitude.getText().toString()) && !isNullOrEmpty(InputPhonenumber.getText().toString())) {
                    Latitude = Double.parseDouble(InputLatitude.getText().toString());
                    Longitude = Double.parseDouble(InputLongitude.getText().toString());
                    Phonenumber = Integer.parseInt(InputPhonenumber.getText().toString());
                }
                Email = InputEmail.getText().toString();
                Website = InputWebsite.getText().toString();
                Birthdate = InputBirthdate.getText().toString();

                if(isValidFriend())
                {
                    Friend f = new Friend(Name, Address, Latitude, Longitude, Phonenumber, Email, Website, Birthdate, "No Path");

                    mDateAccess.insert(f);
                    finish();
                }





            }
        });




        setHomeLocation();



    }

    private boolean isValidFriend() {

        if(!isNullOrEmpty(Name) && !isNullOrEmpty(Address) && Latitude > 0 && Longitude > 0 && Phonenumber > 0 && !isNullOrEmpty(Email) && !isNullOrEmpty(Website) && !isNullOrEmpty(Birthdate))
        {
            return true;
        } else {
            return false;
        }



    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    private void setHomeLocation() {

        Location location = lastKnownLocation();

        if(location == null)
        {
            Toast.makeText(getApplicationContext(), "Last known location is null",
                    Toast.LENGTH_LONG).show();
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        InputLatitude.setText(latitude + "");
        InputLongitude.setText(longitude + "");


    }

    private Location lastKnownLocation() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                return locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        else {
            return locManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        return null;
    }
}
