package com.example.krcrevecproject4;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private ArrayList<String> myList;
    private ArrayAdapter myAdapter;
    private ListFragmentListener listener;

    public ListFragment() {
        // Required empty public constructor
    }
    public interface ListFragmentListener{
        void listClick(int i);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        populateList();
        populateListView(v);


        return v;
    }

    public String getListItem(int i){
        return myList.get(i);
    }

    private void populateList(){
        myList = new ArrayList<>();
        myList.add("About");
        myList.add("Education");
        myList.add("Projects");
        myList.add("Skills");
        myList.add("Links");
        myList.add("Experience");
    }

    private  void  populateListView(View v){
        ListView lv = v.findViewById(R.id.listView);
        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, myList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // send data
                Toast.makeText(getContext(), myList.get(i), Toast.LENGTH_LONG);

                listener.listClick( i);
            }
        });
        lv.setAdapter(myAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener) {
            listener =  (ListFragmentListener) context;

        }

        else{
            throw new RuntimeException(context.toString()
                    + "must implement fragmentAListener");
        }
    }
}
