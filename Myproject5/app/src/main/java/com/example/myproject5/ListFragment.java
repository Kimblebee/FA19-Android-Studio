package com.example.myproject5;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private ListView listView;
    private ArrayList<String> myList;
    ArrayAdapter<String> myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = v.findViewById(R.id.list);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myAdapter);

        return v;
    }



    // update the list depending on the swipe direction
    public void swipe(String s){
        myAdapter.add("You Swiped " + s);
    }

    public void update(String s){
        myAdapter.add(s);
    }





}
