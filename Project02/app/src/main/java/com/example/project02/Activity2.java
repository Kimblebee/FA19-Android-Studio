package com.example.project02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    TextView  quan;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // user enter quantity
        quan =  findViewById(R.id.quantityEntry);
        // user enter name of item
        name = findViewById(R.id.nameEntry);
        // user send



       /* Button btn = (Button)findViewById(R.id.addButton);

        // closes the current page and returns to the previous activity
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i  = new Intent(Activity2.this, MainActivity.class);
                //i.putExtra("key","eggs");
                if(quan.getText() != null && name.getText() != null){

                }
                getIntent().putExtra("food", "eggs");
                getIntent().putExtra("quantity", 5);
                finish();
            }
        });*/
    }

    public void addCallbackFn(View view){
        Intent returnIntent = new Intent(Activity2.this, MainActivity.class);

        if (name.getText().toString().equals("")){
            returnIntent.putExtra("ITEM_NAME", "RANDOM ITEM");

        }
        else
            returnIntent.putExtra("ITEM_NAME", name.getText().toString());

        // in case if the entry for quantity is not a number/blank, default to 1.
        try {
            returnIntent.putExtra("ITEM_QUANTITY", Integer.parseInt(quan.getText().toString()));

        } catch (NumberFormatException e) {
            returnIntent.putExtra("ITEM_QUANTITY", 1);

        }
        setResult(Activity.RESULT_OK, returnIntent);
        // send the data back to mainactivity

        finish();



    }
}
