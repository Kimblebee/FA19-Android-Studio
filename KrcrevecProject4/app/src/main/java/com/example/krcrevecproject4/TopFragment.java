package com.example.krcrevecproject4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {
    ImageView iv;
    TextView name;
    TextView location;


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        iv = v.findViewById(R.id.imageView);
        name = v.findViewById(R.id.name);
        location = v.findViewById(R.id.location);
        setName("Kimberly Crèvecoeur");
        setLocation("Fort Wayne, Indiana");

        return v;
    }



    public void setName(String s){
        this.name.setText(s);

    }

    public void setLocation(String s){
        this.location.setText(s);
    }

    public void setImage(ImageView iv){
        this.iv.setImageResource(iv.getImageAlpha());

    }

}
