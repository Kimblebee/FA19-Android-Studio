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

import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ArrayAdapter<Movie> adapter;
    private ListView listView;
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sets the title of the activity
        getSupportActionBar().setTitle("Crevecoeur's Bluckboster Hits");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        populateMovieList();
        populateListView();





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MovieView.class);
                intent.putExtra("MOVIE_ITEM", movieList.get(i));
                startActivity(intent);
            }
        });
    }




    private class MyListAdapter extends ArrayAdapter<Movie> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_layout, movieList);
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


    public void toFavorites(){
        //Snackbar.make(getCurrentFocus() , "BEHOLD", Snackbar.LENGTH_LONG).setAction("Action", null).show();

         Intent intent = new Intent(MainActivity.this, Favorites.class);
         Bundle b = new Bundle();
         ArrayList<String> names = new ArrayList<>();
         for(Movie m: movieList){
            if( prefs.getBoolean(m.getName(),false)) {
                b.putParcelable(m.getName(), m);
                names.add(m.getName());
            }
         }
         intent.putExtra("Bundle",b);
         intent.putStringArrayListExtra("MOVIE_NAMES", names);

         MainActivity.this.startActivity(intent);
    }


    private void populateMovieList(){
        movieList.add(new Movie("Shreks", 2004, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Paprika", 2007, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Inception", 2012, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Airplane", 2020, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Snakes on a Plane", 2020, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Sharknado", 2020, R.drawable.ic_launcher_background));
        movieList.add(new Movie("Sharknado 2: Electric Boogaloo", 2020, R.drawable.ic_launcher_background));



    }

    private void populateListView(){
        adapter = new MyListAdapter();
        listView = findViewById(R.id.movieListView);
        listView.setAdapter(adapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_favorite){
            toFavorites();
        }

        return super.onOptionsItemSelected(item);
    }
}
