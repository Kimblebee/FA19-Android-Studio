package com.example.myproject03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MovieView extends AppCompatActivity {
    private Movie movie;
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("MOVIE_ITEM");


        setFab();
        setValues();


        //changing the title of the page
        getSupportActionBar().setTitle(movie.getName());

        //return home arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    // applies the onclick listener and sets the heart icon
    public void setFab(){
        final FloatingActionButton fab = findViewById(R.id.fab);

        // if the movie is already favorited, then the icon will be a filled heart.
        if(prefs.getBoolean(movie.getName(), false))
            fab.setImageResource(R.drawable.favorite_on);

        //setting the onClickListener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checking to see if the sharedpreference value is set to favorites
                if(!prefs.getBoolean(movie.getName(),false)) {
                   // movie.setFavorite(1);
                    fab.setImageResource(R.drawable.favorite_on);
                    editor.putBoolean(movie.getName(),true);
                    editor.commit();
                    Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else{
                   // movie.setFavorite(0);
                    fab.setImageResource(R.drawable.ic_action_name);
                    editor.putBoolean(movie.getName(),false);
                    editor.commit();
                    Snackbar.make(view, "Removed from favorites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });
    }

    //sets the values for the activity page items
    public void setValues(){
        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        ImageView picture = findViewById(R.id.image);

        title.setText(movie.getName());
        year.setText(movie.getYear() + "");
        picture.setImageResource(movie.getIcon());





    }

}
