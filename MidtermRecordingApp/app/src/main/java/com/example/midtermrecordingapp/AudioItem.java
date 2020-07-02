package com.example.midtermrecordingapp;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

@SuppressLint("ParcelCreator")
public class AudioItem implements Parcelable {
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    String time;
    String title;
    File file;

    public AudioItem(String time, String title, File f){
        this.time = time;
        this.title = title;
        this.file = f;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(title);
        parcel.writeString(file.toString());
    }
}
