package com.example.midtermrecordingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecordingFragment.RecordingListener, AudioListFragment.AudioListListener {
    RecordingFragment rFrag;
    AudioListFragment lFrag;
    ArrayList<String> files;
    final int REQUEST_PERMISSION = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        files = new ArrayList<>();

        requestPermission();

        if (checkPermissionFromDevice()){

        }

        else {
            requestPermission();
        }

        rFrag = new RecordingFragment();
        lFrag = new AudioListFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, rFrag)
                .commit();
    }



    // taking care of permissions

    // chefck to see if we already have permission
    private boolean checkPermissionFromDevice() {
        int recordPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return recordPerm == PackageManager.PERMISSION_GRANTED &&
                storagePerm == PackageManager.PERMISSION_GRANTED;

    }


    // requesting permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_PERMISSION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }

    }



    @Override
    public void onSave(File f) {
        if (files.contains(f.toString())) {
            files.remove(f.toString());
            Toast.makeText(this, "Overwritten file", Toast.LENGTH_LONG).show();


        }
        files.add(f.toString());
    }

    @Override
    public void viewSaved() {
      //  Toast.makeText(this, "Mainactivity knows you want to view audio", Toast.LENGTH_LONG).show();
        lFrag = new AudioListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("files", files);

        lFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, lFrag)
                .commit();


    }

    @Override
    public void onDelete() {
        files = new ArrayList<>();
        viewSaved();

    }

    @Override
    public void onBack() {
        rFrag = new RecordingFragment();
        Bundle b = new Bundle();
        b.putInt("size", files.size());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, rFrag).commit();
    }
}
