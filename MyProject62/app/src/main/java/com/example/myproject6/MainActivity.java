package com.example.myproject6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int COARSE_LOCATION_PERMISSION_CODE = 1;
    private int FINE_LOCATION_PERMISSION_CODE = 1;
    private FloatingActionButton toListFab, redFab, orangeFab, greenFab, blueFab, purpleFab;
    private Button saveBtn;
    private TextView location;
    private EditText noteEntry;
    private CardView noteBackground;
    int bgColor;
    LocationManager manager;
    Geocoder geocoder;
    protected SQLiteDatabase notesDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize variables
        location = findViewById(R.id.tv_location);
        noteEntry = findViewById(R.id.et_note_entry);
        noteBackground = findViewById(R.id.cv_note_container);
        bgColor = getResources().getColor(R.color.noteRed);
        location.setText("Location (Look at line 59 in Mainactivity)");

        setLocation();
        // set buttons and their onclicks
        setColorFabs();
        setButton();

        // fab to open the list Activity
        toListFab = findViewById(R.id.fab_view_list);
        toListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                viewNotesList();
            }
        });

        // sets the database
        createDatabase(findViewById(android.R.id.content));


    }


    private void setColorFabs() {
        // colored fabs
        redFab = findViewById(R.id.fab_red);
        orangeFab = findViewById(R.id.fab_orange);
        greenFab = findViewById(R.id.fab_green);
        blueFab = findViewById(R.id.fab_blue);
        purpleFab = findViewById(R.id.fab_purple);


        redFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                changeBackground(0);
            }
        });

        orangeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                changeBackground(1);
            }
        });

        greenFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                changeBackground(2);
            }
        });

        blueFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                changeBackground(3);
            }
        });

        purpleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                changeBackground(4);
            }
        });

    }

    // given an int 0-4, changes the text background to the respective color
    private void changeBackground(int i) {
        if (i == 0) {
            bgColor = getResources().getColor(R.color.noteRed);
            noteBackground.setCardBackgroundColor(bgColor);
        }

        if (i == 1) {
            bgColor = getResources().getColor(R.color.noteOrange);
            noteBackground.setCardBackgroundColor(bgColor);
        }

        if (i == 2) {
            bgColor = getResources().getColor(R.color.noteGreen);
            noteBackground.setCardBackgroundColor(bgColor);
        }

        if (i == 3) {
            bgColor = getResources().getColor(R.color.noteBlue);
            noteBackground.setCardBackgroundColor(bgColor);
        }

        if (i == 4) {
            bgColor = getResources().getColor(R.color.notePurple);
            noteBackground.setCardBackgroundColor(bgColor);
        }
    }

    // sets the save note button
    private void setButton() {
        saveBtn = findViewById(R.id.btn_add);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDbItem();
            }
        });
    }


    // sets the location to the textview
    private void setLocation() {


        // checking permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission for location granted", Toast.LENGTH_LONG).show();

            getLocation();

        }
        else{
            Toast.makeText(this, "Permission for location not granted", Toast.LENGTH_LONG).show();

            requestLocationPermission();
        }



    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        double latitude;
        double longitude;

        List<Address> addresses;

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //textView = findViewById(R.id.textview);
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //uncomment these to see the error. it says 'grpc failed'.

            latitude = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            longitude = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String buildingname = addresses.get(0).getLocality();
            location.setText(buildingname);
        }

        catch (IOException e) {
            location.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
    }

    // requests for location perms
    private void requestLocationPermission() {
        // requesting permissions for fne/coarse locations
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, FINE_LOCATION_PERMISSION_CODE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
    }




    //opens the NoteList Activity to view saved notes
    private void viewNotesList() {
        Intent intent = new Intent(getBaseContext(), NoteList.class);
        Bundle b = new Bundle();
        b.putString("DB_PATH", getApplicationContext().getDatabasePath("MyNotes.db").getAbsolutePath());
        intent.putExtra("BUNDLE", b);
        //intent.putExtra("DATABASE", notesDb);
        startActivity(intent);
    }



    //DATABASE METHODS
    // has columns: text, date, location, color. called MyNotes
    public void createDatabase(View view) {
        // has columns: text, date, location, color

        try {
            notesDb = this.openOrCreateDatabase("MyNotes", MODE_PRIVATE, null);
            notesDb.execSQL("CREATE TABLE IF NOT EXISTS MyNotes " +
                    "(id integer primary key, text VARCHAR, date VARCHAR, location VARCHAR, color INT);");
        } catch (Exception e) {
            Toast.makeText(this, "Database creation error", Toast.LENGTH_SHORT).show();

            //Log.v("DB_ERROR", "Error Creating the Database!");
        }
    }

    public void addDbItem() {
        if (noteEntry.getText().toString().trim().length() > 0){

        try {

                // get the text from the note, the current date, the current location, and the current note color
                String text = noteEntry.getText().toString();
                String date = new SimpleDateFormat("MM/dd/yyy", Locale.getDefault()).format(new Date());
                String place = location.getText().toString();
                // int bgColor


                // inserting the data into the table
                notesDb.execSQL("INSERT INTO MyNotes (text, date, location, color) " +
                        "VALUES('" + text + "','" + date + "','" + place + "','" + bgColor + "');");

                // clears the editText for the note
                noteEntry.setText("");
                Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, "Failed to add Note", Toast.LENGTH_SHORT).show();

            }
        }

        else {
            Toast.makeText(this, "Write something!", Toast.LENGTH_SHORT).show();
        }

    }

}



