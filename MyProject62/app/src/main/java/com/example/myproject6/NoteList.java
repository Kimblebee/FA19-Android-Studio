package com.example.myproject6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteList extends AppCompatActivity {
    ListView listView;

    ArrayList<Note> noteObjList;
    ArrayList<String> notesList;
    MyListAdapter myAdapter;
    SQLiteDatabase notesDb = null;
    EditText searchbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        listView = findViewById(R.id.lv_note_list);
        searchbar = findViewById(R.id.et_searchbar);

        setMyDb();
        populateNoteObjList();
        populateNotesList();


        myAdapter = new NoteList.MyListAdapter();
        listView.setAdapter(myAdapter);


        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    myAdapter.getFilter().filter("soup");

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        // shows a back arrow button on the top of the menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void populateNoteObjList(){
        noteObjList = new ArrayList<>();
        Cursor cursor = notesDb.rawQuery("SELECT * from MyNotes", null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            do {
                int indxColumn_id = cursor.getColumnIndex("id");
                int indxColumn_text = cursor.getColumnIndex("text");
                int indxColumn_date = cursor.getColumnIndex("date");
                int indxColumn_location = cursor.getColumnIndex("location");
                int indxColumn_color = cursor.getColumnIndex("color");


                String id = cursor.getString(indxColumn_id);
                String text = cursor.getString(indxColumn_text);
                String date = cursor.getString(indxColumn_date);
                String location = cursor.getString(indxColumn_location);
                int color = cursor.getInt(indxColumn_color);

                Note n = new Note(id, text, date, location, color);
                noteObjList.add(n);



            } while (cursor.moveToNext());
            cursor.close();

            //Toast.makeText(this, idList.size() + "", Toast.LENGTH_SHORT).show();
        }

        // if there is nothing, tell them
        else{
            Toast.makeText(this, "No items to display!", Toast.LENGTH_SHORT).show();
        }
    }
    private void populateNotesList(){
        notesList = new ArrayList<>();
        for (Note note: noteObjList){
            notesList.add(note.getText());
        }
    }
    private ArrayList<Note> filterNote(String entry){
        ArrayList<Note> filteredList = noteObjList;

        for (Note note: filteredList){
            if (!note.getText().toLowerCase().contains(entry.toLowerCase())){
                filteredList.remove(note);
            }
        }
        return filteredList;
    }

    private class MyListAdapter extends ArrayAdapter {

        public MyListAdapter() {
            super(NoteList.this, R.layout.item_layout, notesList);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;

            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.item_layout, parent, false);

                //TODO

                // setting the text in the TextView
                final TextView noteText = itemView.findViewById(R.id.tv_noteText);
                String text = notesList.get(position);
                noteText.setText(text);

                //setting the location and date in the TextView
                TextView noteDate = itemView.findViewById(R.id.tv_LocationAndDate);
                String date = noteObjList.get(position).getDate();
                String location = noteObjList.get(position).getLocation();
                noteDate.setText(location + ", " + date);


                //setting the note color
                CardView background = itemView.findViewById(R.id.cv_background);
                int color = noteObjList.get(position).getColor();
                background.setCardBackgroundColor(color);


                // sets the button to delete the entry
                ImageButton imgButton = itemView.findViewById(R.id.btn_delete);
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            // deletes from database and the adapter
                            deleteDbItem(noteObjList.get(position).getId());
                            // noteObjList.remove(noteObjList.get(position));
                             myAdapter.remove(myAdapter.getItem(position));
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Cannot delete: " + e.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

                    // end if statement

            return itemView;


        }




    }




    private void setMyDb(){
        notesDb = getApplicationContext().openOrCreateDatabase("MyNotes", MODE_PRIVATE, null);
    }
    public void deleteDbItem(String ID) {

        // try to delete the note entry with the given id
        try {
            notesDb.execSQL("DELETE FROM myNotes WHERE id = " + ID + ";");
            Toast.makeText(this, ID + " Note successfully deleted", Toast.LENGTH_SHORT).show();

        }

        // if it fails to delete the note, say so.
        catch (Exception e){
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }
    }







}
