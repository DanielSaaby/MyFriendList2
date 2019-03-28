package com.example.myfriendlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfriendlist.Interface.IViewCallBack;
import com.example.myfriendlist.Model.Friend;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements IViewCallBack {
    String TAG = MainActivity.TAG;


    private final static String LOGTAG = "Camtag";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static int CAMERA_REQUEST_CODE = 4;

    LocationListener locLisenter;
    LocationManager locManager;

    CameraDevice cameraDevice;
    CameraManager cameraManager;
    int cameraFacing;
    String cameraId;
    CameraCaptureSession cameraCaptureSession;

    TextureView.SurfaceTextureListener surfaceTextureListener;
    TextureView textureView;

    private Size previewSize;

    String phoneNumber = "26294128";

    TextView txtDistance;
    ImageView imageTaken;

    File mFile;

    Friend f;

    TextView vName;
    TextView vAddress;
    TextView vPhoneNumber;
    TextView vEmail;
    TextView vWebsite;
    TextView vBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Log.d(TAG, "Detail Activity started");



        vName = findViewById(R.id.vName);
        vAddress = findViewById(R.id.vAddress);
        vPhoneNumber = findViewById(R.id.vPhone);
        vEmail = findViewById(R.id.vEmail);
        vWebsite = findViewById(R.id.vWebsite);
        vBirthday = findViewById(R.id.vBirthday);



        locManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setGUI();





    }

    private void calcDistanceToFriend()  {
        Location location = lastKnownLocation();

        if(f.getLongitude() != 0 && f.getLongitude() != 0)
        {
            Location friendsLocation = new Location("Loc");
            friendsLocation.setLatitude(f.getLatitude());
            friendsLocation.setLongitude(f.getLongitude());

            float distance = location.distanceTo(friendsLocation);

            txtDistance.setText("Distance: " + distance + "m");
        }

        log("Friend doesn't have a home");




    }

    private void OpenFacebookProfile() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(f.getWebsite()));
        startActivity(intent);
    }

    private void SendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { f.getEMail() };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Andriod Studio Course");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey, Hope that it is ok, Best Regards android...;-)");
        startActivity(emailIntent);
    }

    private void SendText() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + f.getPhoneNumber()));
        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...");
        startActivity(sendIntent);
    }

    private void MakeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + f.getPhoneNumber()));
        startActivity(intent);
    }


    /** Create a File for saving an image */
    private File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera01");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                log("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String postfix = "jpg";
        String prefix = "IMG";

        File mediaFile = new File(mediaStorageDir.getPath() +
                File.separator + prefix +
                "_" + timeStamp + "." + postfix);

        return mediaFile;
    }



    public void onTakePhotoButtonClicked() {
        mFile = getOutputMediaFile(); // create a file to save the image
        Log.d(LOGTAG, mFile.toString());
        if (mFile == null) {
            Toast.makeText(this, "Could not create file...", Toast.LENGTH_LONG).show();
            return;
        }

        //com.example.homefolder.example.provider

        // create Intent to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                this,
                "com.example.homefolder.example.provider", //(use your app signature + ".provider" )
                mFile));

        Log.d(LOGTAG, "file uri = " + Uri.fromFile(mFile).toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d(LOGTAG, "camera app will be started");
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else
            Log.d(LOGTAG, "camera app could NOT be started");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(LOGTAG, mFile.toString());
                showPictureTaken(mFile);
                f.setImgPath(mFile.getPath());
            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                return;

            } else
                Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show();
        }
    }

    private void showPictureTaken(File f) {
        imageTaken.setImageURI(Uri.fromFile(f));
        imageTaken.setBackgroundColor(Color.RED);
    }


    private void setGUI() {

        f = (Friend) getIntent().getSerializableExtra("friend");
        /*if (f.getImgPath() != null) {
            this.imageTaken.setImageURI(Uri.parse(f.getImgPath()));
        }*/



        vName.setText(f.getName());
        vAddress.setText(f.getAddress());
        vPhoneNumber.setText("" + f.getPhoneNumber());
        vEmail.setText(f.getEMail());
        vBirthday.setText(f.getBirthday());



        findViewById(R.id.enterCameraBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakePhotoButtonClicked();
            }
        });




        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCall();
            }
        });

        findViewById(R.id.textBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendText();
            }
        });



        findViewById(R.id.emailBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
            }
        });

        findViewById(R.id.homeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHomeLocation();
            }
        });

        findViewById(R.id.vWebsiteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFacebookProfile();
            }
        });

    }

    private void setHomeLocation() {

        Intent intent = new Intent(DetailActivity.this, MapActivity.class);
        startActivity(intent);
        /*
        Location location = lastKnownLocation();

        if(location == null)
        {
            Toast.makeText(getApplicationContext(), "Last known location is null",
                    Toast.LENGTH_LONG).show();
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        f.setLatitude(latitude);
        f.setLongitude(longitude);

        Toast.makeText(getApplicationContext(), "Latitude" +f.getLatitude(),
                Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), "Longitude" +f.getLongitude(),
                Toast.LENGTH_LONG).show();

*/
    }

    private Location lastKnownLocation() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                    log("Inde i loop");
                return locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        else {
                log("ude af loop");
            return locManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        return null;
    }


    void log(String s) {
        Log.d(LOGTAG, s);
    }


    @Override
    public void setSpeed(double speed) {

    }

    @Override
    public void setCurrentLocation(Location location) {

    }

    @Override
    public void setCounter(int conut) {

    }
}