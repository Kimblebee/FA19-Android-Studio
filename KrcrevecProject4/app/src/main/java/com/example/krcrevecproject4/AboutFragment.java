package com.example.krcrevecproject4;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private AboutFragment.OnNextListener listener;
    private FloatingActionButton fabEdit;
    private TextView title;
    private TextView info;
    private String tString;
    private String dString;




    public AboutFragment() {
        // Required empty public constructor
    }


    public interface OnNextListener{
         void onNext();
         void onEdit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        title = v.findViewById(R.id.titleView);
        info = v.findViewById(R.id.infoView);

        try {
            title.setText(b.getString("TITLE"));
            info.setText(b.getString("INFO"));
        }
        catch (Exception e){
            title.setText(e.toString());
        }


        setFab(v);

        return v;
    }


    public void setTitle(String s){
        title.setText(s);
    }

    public void setInfo(String s){
        info.setText(s);
    }

    // makes the edit button invisible
    public void hideEdit(){
        fabEdit.hide();
    }

    // makes the edit button visible
    public void viewEdit(){
        fabEdit.show();
    }

    // adds actionlisteners to the fabs
    public void setFab(View v){
         fabEdit = v.findViewById(R.id.fabEdit);
         FloatingActionButton fabNext = v.findViewById(R.id.fabNext);

         // // when you click on the edit button, open up the edit fragment
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                // on click, send information to the mainactivity
                listener.onEdit();

            }
        });


         // functionality to move to the next list item
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                // on click, send information to the mainactivity
                listener.onNext();

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutFragment.OnNextListener) {
            listener =  (AboutFragment.OnNextListener) context;

        }

        else{
            throw new RuntimeException(context.toString()
                    + "must implement onNextListener");
        }
    }



}
