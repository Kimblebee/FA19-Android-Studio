package com.example.krcrevecproject4;
import android.widget.ImageView;

public class User {
    private String name;
    private String about;
    private String location;
    private String education;
    private String projects;
    private String skills;
    private String links;
    private String experience;
    private ImageView photo;

    public User(String name, String location){
        this.name = name;
        this.location = location;

        about = "edit this information";
        projects = "A ton of projects";
        education = "Indiana University \n B.S. Computer Science \n 2021";
        skills = "Petting dogs \n Petting dogs \n Java(?)";
        links = "linkedin.com/in/kimcrev";
        experience = "NONE LOL";
        //photo.setImageResource( R.mipmap.ic_launcher_round);


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }
}
