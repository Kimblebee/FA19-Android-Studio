package com.example.myproject5;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


    public ImageFragment() {
        // Required empty public constructor
    }
    ImageView iv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        iv = v.findViewById(R.id.imageView);

        iv.setImageResource(R.drawable.down);

        return v;
    }



    public void changeImage(String s){
        if(s.equals("up")){
            iv.setImageResource(R.drawable.up);
        }

        else if (s.equals("down")){
            iv.setImageResource(R.drawable.down);
        }
        else if (s.equals("left")){
            iv.setImageResource(R.drawable.left);
        }
        else if (s.equals("right")){
            iv.setImageResource(R.drawable.right);
        }

    }


}
