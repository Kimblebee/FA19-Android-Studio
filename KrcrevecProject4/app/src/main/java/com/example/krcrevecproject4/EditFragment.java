package com.example.krcrevecproject4;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {
    private EditFragment.EditListener listener;
    private EditText text;



    public EditFragment() {
        // Required empty public constructor
    }

    public interface EditListener{
        void onSubmit(String s);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        setFab(v);
        text = v.findViewById(R.id.editText);

        return v;
    }

    public void setFab(View v){
        FloatingActionButton floatingActionButton = v.findViewById(R.id.submitFab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                // on click, send information to the mainactivity
                listener.onSubmit(text.getText().toString());

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditFragment.EditListener) {
            listener =  (EditFragment.EditListener) context;

        }

        else{
            throw new RuntimeException(context.toString()
                    + "must implement EditListener");
        }
    }
}
