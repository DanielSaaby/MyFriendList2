package com.example.myfriendlist;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfriendlist.Interface.IDataAccess;
import com.example.myfriendlist.Model.Friend;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFriendActivity extends AppCompatActivity {

    private IDataAccess mDateAccess;
    private LocationManager locManager;
    final Calendar myCalendar = Calendar.getInstance();
    
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

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


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

        InputBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Name = InputName.getText().toString().trim();
                Address = InputAddress.getText().toString().trim();
                if (!isNullOrEmpty(InputLatitude.getText().toString()) && !isNullOrEmpty(InputLongitude.getText().toString()) && !isNullOrEmpty(InputPhonenumber.getText().toString())) {
                    Latitude = Double.parseDouble(InputLatitude.getText().toString());
                    Longitude = Double.parseDouble(InputLongitude.getText().toString());
                    Phonenumber = Integer.parseInt(InputPhonenumber.getText().toString());
                }
                Email = InputEmail.getText().toString();
                Website = InputWebsite.getText().toString();
                Birthdate = InputBirthdate.getText().toString();

                if (isValidFriend()) {
                    Friend f = new Friend(Name, Address, Latitude, Longitude, Phonenumber, Email, Website, Birthdate, "No Path");

                    mDateAccess.insert(f);
                    finish();
                } else {
                    Toast.makeText(v.getContext(), "Please fill all the required fields", Toast.LENGTH_LONG).show();
                }


            }
        });


        setHomeLocation();


    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        InputBirthdate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean isValidFriend() {

        if (!isNullOrEmpty(Name) && !isNullOrEmpty(Address) && Latitude > 0 && Longitude > 0 && Phonenumber > 0 && !isNullOrEmpty(Email) && !isNullOrEmpty(Birthdate)) {
            return true;
        } else {
            return false;
        }


    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    private void setHomeLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

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


}
