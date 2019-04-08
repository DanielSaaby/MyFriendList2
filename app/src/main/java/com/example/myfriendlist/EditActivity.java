package com.example.myfriendlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfriendlist.Model.Friend;

public class EditActivity extends AppCompatActivity {


    private Friend f;
    Friend updatedFriend;
    TextView txtEditFriend;

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
        setContentView(R.layout.activity_edit);

        f = (Friend) getIntent().getSerializableExtra("friend");

        txtEditFriend = findViewById(R.id.txtEditFriend);
        txtEditFriend.setText("Edit Friend");


        InputName = findViewById(R.id.inpEditName);
        InputAddress = findViewById(R.id.inpEditAddress);
        InputLatitude = findViewById(R.id.inpEditLatitude);
        InputLongitude = findViewById(R.id.inpEditLongitude);
        InputPhonenumber = findViewById(R.id.inpEditPhonenumber);
        InputEmail = findViewById(R.id.inpEditEmail);
        InputWebsite = findViewById(R.id.inpEditWebsite);
        InputBirthdate = findViewById(R.id.inpEditBirthdate);

        initInfoSetup();


        findViewById(R.id.btnEditSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFriend();
            }
        });


    }

    /**
     * Updates a specific friend
     */
    private void updateFriend() {

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
            updatedFriend = new Friend(Name, Address, Latitude, Longitude, Phonenumber, Email, Website, Birthdate, f.getImgPath());

            Intent intent = new Intent();
            intent.putExtra("friend", updatedFriend);
            setResult(101, intent);

            finish();
        } else {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets up initial info for a friend edit
     */
    private void initInfoSetup() {
        InputName.setText(f.getName());
        InputAddress.setText(f.getAddress());
        InputLatitude.setText(f.getLatitude() + "");
        InputLongitude.setText(f.getLongitude() + "");
        InputPhonenumber.setText(f.getPhoneNumber() + "");
        InputEmail.setText(f.getEMail());
        InputWebsite.setText(f.getWebsite());
        InputBirthdate.setText(f.getBirthday());
    }

    /**
     * checks if a given friend is valid
     * @return
     */
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
}
