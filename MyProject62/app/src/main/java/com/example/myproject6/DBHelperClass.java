package com.example.myproject6;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperClass extends SQLiteOpenHelper {

    // has columns: text, date, location, color. called MyNotes

    public static final String DATABASE_NAME = "MyNotes.db";
    public static final String TABLE_NAME = "Notes_Table";
    public static final String COLUMN_1 = "Id";
    public static final String COLUMN_2 = "NoteText";
    public static final String COLUMN_3 = "Date";
    public static final String COLUMN_4 = "Location";
    public static final String COLUMN_5 = "Color";

    public DBHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        //For Testing Purposes Only
        //SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTETEXT TEXT, DATE TEXT, LOCATION TEXT, COLOR TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String tName, String tEmail, String tPhone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, tName);
        contentValues.put(COLUMN_3, tEmail);
        contentValues.put(COLUMN_4, tPhone);
        long result = db.insert(TABLE_NAME, null, contentValues); //return -1 if error
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
