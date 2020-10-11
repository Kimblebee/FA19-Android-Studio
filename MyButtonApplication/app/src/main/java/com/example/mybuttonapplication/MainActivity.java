package com.example.mybuttonapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // Creating my button
        // you gotta find the id and set it to this variable
        Button convert = findViewById(R.id.convertbtn);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView input = findViewById(R.id.inputMin);
                TextView output = findViewById(R.id.result);
                String numString = input.getText().toString();


                try
                {
                    int i = Integer.parseInt(numString);
                    output.setText("" + minToSec(i));

                    Toast.makeText(getApplicationContext(), "converting", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),number, Toast.LENGTH_SHORT).show();

                }
                catch(NumberFormatException e)
                {
                    output.setText("ERROR");
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public int minToSec(int input){
        return input * 60;
    }

}
