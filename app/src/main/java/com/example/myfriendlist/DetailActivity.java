package com.example.myfriendlist;

import android.Manifest;
import android.app.Activity;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfriendlist.Interface.IDataAccess;
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
    private final static int UPDATED_FRIEND_REQUEST_CODE = 101;

    private IDataAccess mDateAccess;

    LocationManager locManager;

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
        mDateAccess = DataAccessFactory.getInstance(this);

        f = (Friend) getIntent().getSerializableExtra("friend");



        vName = findViewById(R.id.vName);
        vAddress = findViewById(R.id.vAddress);
        vPhoneNumber = findViewById(R.id.vPhone);
        vEmail = findViewById(R.id.vEmail);
        vWebsite = findViewById(R.id.vWebsite);
        vBirthday = findViewById(R.id.vBirthday);

        imageTaken = findViewById(R.id.imageTaken);


        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setGUI();
    }


    /**
     * Opens the website of the current friend
     */
    private void OpenWebsiteProfile() {
        if(f.getWebsite() != null && !f.getWebsite().isEmpty()) {

            String url = f.getWebsite();
            Uri webpage;

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            } else {
                webpage = Uri.parse(url);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(intent);
        }
        else {
           Toast toast = Toast.makeText(this, "There is no Website available for this friend", Toast.LENGTH_LONG);
           toast.setGravity(Gravity.CENTER, 0,0);
           toast.show();
        }
    }

    /**
     * Opens the phones default Email browser with info on current friend
     */
    private void SendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = {f.getEMail()};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Andriod Studio Course");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey, Hope that it is ok, Best Regards android...;-)");
        startActivity(emailIntent);
    }

    /**
     * Opens the phones default SMS browser with info from current friend
     */
    private void SendText() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + f.getPhoneNumber()));
        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...");
        startActivity(sendIntent);
    }

    /**
     * Opens the phones default call interface
     */
    private void MakeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + f.getPhoneNumber()));
        startActivity(intent);
    }

    /**
     * Gets the media file from local storage or creates one if it does not exist
     * @return
     */
    private File getOutputMediaFile() {
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

    /**
     * Opens the phones camera app
     */
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

        switch (requestCode)
        {

            case(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) : {
                if (resultCode == RESULT_OK) {
                    Log.d(LOGTAG, mFile.toString());
                    showPictureTaken(mFile);
                    f.setImgPath(mFile.getPath());
                    mDateAccess.update(f);
            } else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                return;
            }
            }

            case (UPDATED_FRIEND_REQUEST_CODE) : {
                if(resultCode == 101) {
                    f = (Friend)data.getExtras().getSerializable("friend");
                    mDateAccess.update(f);
                    setGUI();
                }
            }
        }
    }

    /**
     * Sets current image for the user image based on input file.
     * @param f
     */
    private void showPictureTaken(File f) {
        imageTaken.setImageURI(Uri.fromFile(f));
    }

    /**
     * Sets the UI of the application on creation
     */
    private void setGUI() {

        vName.setText(f.getName());
        vAddress.setText(f.getAddress());
        vPhoneNumber.setText("" + f.getPhoneNumber());
        vEmail.setText(f.getEMail());
        if(f.getWebsite() != null && !f.getWebsite().isEmpty())
        {
            vWebsite.setText(f.getWebsite());
        } else {
            vWebsite.setText("No Website Available");
        }
        if(!f.getImgPath().isEmpty() && !f.getImgPath().equals("No Path"))
        {
            imageTaken.setImageURI(Uri.fromFile(new File(f.getImgPath())));
        } else
        {
            imageTaken.setImageResource(R.drawable.placeholder);
        }

        vBirthday.setText(f.getBirthday());

        findViewById(R.id.imageTaken).setOnClickListener(new View.OnClickListener() {
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
                OpenWebsiteProfile();
            }
        });

        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateAccess.deleteById(f.getId());
                finish();
            }
        });

        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("friend", f);

                startActivityForResult(intent, 101);
            }


        });

        findViewById(R.id.showBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * Sets the Home Location of the friend
     */
    private void setHomeLocation() {
        Intent intent = new Intent(DetailActivity.this, MapActivity.class);
        intent.putExtra("friend", f);
        startActivity(intent);
    }

    /**
     * Logs the given message with the log tag "TAG"
     * @param s
     */
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