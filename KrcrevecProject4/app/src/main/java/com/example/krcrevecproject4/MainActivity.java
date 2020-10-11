package com.example.krcrevecproject4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements ListFragment.ListFragmentListener, EditFragment.EditListener, AboutFragment.OnNextListener {

    private TopFragment tFrag;
    private ListFragment lFrag;
    private EditFragment eFrag;
    private AboutFragment aFrag;
    private HashMap<Integer, String> map;
    private int listPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = new HashMap<>();
        setInfo();

        listPos = 0;
        tFrag = new TopFragment();
        lFrag = new ListFragment();
        eFrag = new EditFragment();
        aFrag = new AboutFragment();

        Bundle b = new Bundle();
        b.putString("TITLE", "About Me");
        b.putString("INFO", map.get(0));
        aFrag.setArguments(b);



        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.list_container, lFrag)
                .replace(R.id.top_container, tFrag)
                .replace(R.id.info_container, aFrag);



        transaction.commit();

    }

    public String getDesc(){
        return map.get(0);
    }

    public void setInfo(){
        /*
        0 = about
        1 = education
        2 = projects
        3 = skills
        4 = links
        5 = experience
         */
        map.put(0, "Just a college student trying to get by. " +
                "My dietary intake is total garbage. ");
        map.put(1, "IUB 2021 B.S. CS... hopefully?");
        map.put(2, "This is my projects!");
        map.put(3, "Java (i think)\nA smidge of Android Studio\nSome C\nA pinch of Python \nA cup of JavaScript");
        map.put(4, "linkedin.com/in/kimcrev");
        map.put(5, "Computer Science Tutor - 2018- \nNSBE Executive - 2019-20\nSICE LLC Peer Mentor - 2018-19");
    }

    // handler for clicking on list items - change the right activity contents
    @Override
    public void listClick( int i) {
        // depending on the list item clicked, change right fragment contents
        //tFrag.setName(s);
        aFrag.setTitle(lFrag.getListItem(i));
        aFrag.setInfo(map.get(i));
        listPos = i;

        if (i == 0){

            aFrag.viewEdit();
        }
        else {
            aFrag.hideEdit();
        }
    }

    // handler for submitting the new edited "About" description
    @Override
    public void onSubmit(String s) {
        // change the stored About Me data
        map.put(0, s);

        // pass new about me data into the AboutFragment
        Bundle b = new Bundle();
        b.putString("TITLE", "About Me");
        b.putString("INFO", map.get(0));
        aFrag.setArguments(b);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.info_container, aFrag ).commit();

    }

    // handler for the next button; change the right fragment contents
    @Override
    public void onNext() {
        if (listPos == 5){
            listPos = 0;
        }
        else {
            listPos++;
        }

        listClick(listPos);

    }

    @Override
    public void onEdit() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.info_container, eFrag )
                .replace(R.id.top_container, tFrag).commit();
    }
}
