package com.example.myproject6;

public class Note {
    private String text;
    private String id;
    private String date;
    private String location;
    private int color;

    public Note( String id, String text, String date, String loc, int color){
        this.text = text;
        this.id = id;
        this.date = date;
        this.location = loc;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
