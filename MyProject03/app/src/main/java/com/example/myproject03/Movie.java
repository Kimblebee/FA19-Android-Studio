package com.example.myproject03;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {


    public Movie(String name, int year, int icon){
        this.name = name;
        this.year = year;
        this.icon = icon;

    }
    private String name;
    private int year;
    private int icon;
   // private int favorite;

    protected Movie(Parcel in) {
        name = in.readString();
        year = in.readInt();
        icon = in.readInt();
        //favorite = 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

  /*  public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(year);
        parcel.writeInt(icon);
        //parcel.writeInt(favorite);
    }
}

