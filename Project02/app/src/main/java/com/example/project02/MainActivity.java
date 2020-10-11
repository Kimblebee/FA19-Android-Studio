package com.example.project02;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;
        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> myAdapter;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    //boolean to check if you can remove an item
    private boolean remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        remove = false;
        populateListView();


        // whenever you click the '-' button and then click on a list item, delete that list item
        final ListView myListView = findViewById(R.id.list);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //you must click the '-' button once every time you want to remove something
                if (remove == true) {
                    myAdapter.remove(myAdapter.getItem(position));
                    remove = false;
                }
            }
        });

    }

    // method used by the add buttons to get data from the next activity
    public void addButtonFn(View view){
        Intent i = new Intent(MainActivity.this, Activity2.class);
        startActivityForResult(i, 67);

    }


    // when getting data from input, go through this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            //textview1.setText("User Cancelled");
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 67:
                    int quant = data.getIntExtra("ITEM_QUANTITY", 1);
                    String name = data.getStringExtra("ITEM_NAME");
                    addItem(name,quant);

                    // putting the data into sharedpreferences
                    editor.putInt(name,quant);
                    editor.commit();

                    break;
            }
        }
    }


    private void addItem(String name, int quan) {
        myAdapter.add(name + ", " + quan);
    }

    public void removeButtonFn(View view){
        remove = true;
    }




    private void populateListView() {

        //Create a list of items (Strings)
        //Create an Adapter to convert items to Views (viewItems, UI for each)
        //Configure listView to accept views thru adapter
        ArrayList<String> myItems = new ArrayList<>();
        myItems.add("pancakes, 10");
        myItems.add("burgers, 7");
        myItems.add("hotdogs, 7");
        myItems.add("sadness, 1");


        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myItems);
        ListView myListViewer = findViewById(R.id.list);
        myListViewer.setAdapter(myAdapter);





    }

}
