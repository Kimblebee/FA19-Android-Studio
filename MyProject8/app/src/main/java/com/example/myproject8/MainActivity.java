package com.example.myproject8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int PERMISSION_REQUEST_CODE = 58;
    EditText editName;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.et_name);
        login = findViewById(R.id.btn_login);
        setListeners();

        requestPermission();
    }

    private void setListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity();
            }
        });


    }

    // request permission for reading external storage
    void requestPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permisson to read contacts granted", Toast.LENGTH_LONG);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
        }
    }


    void startNextActivity(){
        Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
        intent.putExtra("MY_NAME", editName.getText().toString());
        startActivity(intent);
    }

}
