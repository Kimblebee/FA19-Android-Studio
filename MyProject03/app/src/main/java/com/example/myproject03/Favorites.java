package com.example.myproject03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ArrayAdapter<Movie> adapter;
    private ListView listView;
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sets the title of the activity
        getSupportActionBar().setTitle("Favorite Movies");
        //enables home back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // fills the list of movies
        populateMovieList();
        // puts the movies into the arrayadapter
        populateListView();
    }

    //take the movies from the Bundle and put them into an arrayList to be used by the class
    private void populateMovieList(){
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("Bundle");
        ArrayList<String> names = intent.getStringArrayListExtra("MOVIE_NAMES");
        for (String s: names){
            Movie m = b.getParcelable(s);
            movieList.add(m);
        }
    }

    // populating the adapter with the movie items
    private void populateListView(){
        adapter = new Favorites.MyListAdapter();
        listView = findViewById(R.id.movieListView);
        listView.setAdapter(adapter);
    }


    private class MyListAdapter extends ArrayAdapter<Movie> {
        public MyListAdapter() {
            super(Favorites.this, R.layout.item_layout, movieList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;

            if(itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.item_layout, parent, false);

            Movie currentMovie = movieList.get(position);

            TextView itemName = itemView.findViewById(R.id.movie_name);
            itemName.setText(currentMovie.getName());

            //??????????
            TextView itemNumber = itemView.findViewById(R.id.movie_year);
            itemNumber.setText(""+currentMovie.getYear());

            ImageView itemIcon = itemView.findViewById(R.id.movie_icon);
            itemIcon.setImageResource(currentMovie.getIcon());
            return itemView;

        }
    }


}
