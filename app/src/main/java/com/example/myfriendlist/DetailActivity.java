package com.example.myfriendlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfriendlist.Model.Friend;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    String TAG = MainActivity.TAG;


    private final static String LOGTAG = "Camtag";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static int CAMERA_REQUEST_CODE = 4;

    CameraDevice cameraDevice;
    CameraManager cameraManager;
    int cameraFacing;
    String cameraId;
    CameraCaptureSession cameraCaptureSession;

    TextureView.SurfaceTextureListener surfaceTextureListener;
    TextureView textureView;

    private Size previewSize;

    String phoneNumber = "26294128";



    TextView mFilename;

    EditText editName;
    EditText editAge;
    ImageView imageTaken;

    File mFile;

    Friend f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Log.d(TAG, "Detail Activity started");

        ArrayList<String> permissions = new ArrayList<String>();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.CAMERA);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissions.size() > 0)
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 1);


        textureView = findViewById(R.id.textureView);
        imageTaken = findViewById(R.id.imageTaken);
        mFilename = findViewById(R.id.fileName);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);


        setGUI();

    }

    private void SendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { "daniel@wefly4you.dk" };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Andriod Studio Course");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey, Hope that it is ok, Best Regards android...;-)");
        startActivity(emailIntent);
    }

    private void SendText() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + phoneNumber));
        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...");
        startActivity(sendIntent);
    }

    private void MakeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
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
        if (f.getImgPath() != null) {
            this.imageTaken.setImageURI(Uri.parse(f.getImgPath()));
        }

        editName.setText(f.getName());
        editAge.setText("" + f.getAge());

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

    }


    void log(String s) {
        Log.d(LOGTAG, s);
    }


}