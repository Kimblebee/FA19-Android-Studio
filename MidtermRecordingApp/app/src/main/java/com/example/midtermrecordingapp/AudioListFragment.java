package com.example.midtermrecordingapp;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioListFragment extends Fragment {
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myList;
    ListView listView;
    FloatingActionButton backFab;
    FloatingActionButton trashFab;
    AudioListListener listener;
    MediaPlayer player;
   // Boolean playing;

    public interface AudioListListener{
        void onDelete();
        void onBack();
    }




    public AudioListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





        View v = inflater.inflate(R.layout.fragment_audio_list, container, false);

        //playing = false;
        if(myList == null){
            populateList();
        }

        populateListView(v);

        player = new MediaPlayer();
        backFab = v.findViewById(R.id.backFab);
        trashFab = v.findViewById(R.id.trashFab);


        // button onclick to go back
        backFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                listener.onBack();
            }
        });

        // button onclick to delete all
        trashFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                deleteAudio();
                listener.onDelete();
            }
        });


        return v;
    }

    // creating the new arraylist
    public void populateList(){
        myList = new ArrayList<>();
    }

    // creating the listview
    private  void populateListView(View v){
        listView = v.findViewById(R.id.lv);
        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, myList);

        // setting onclick listener to play audio when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playAudio(myList.get(i));
            }
        });

        listView.setAdapter(myAdapter);
    }


    // delete all saved audio
    private void deleteAudio(){
        //myAdapter = new ArrayAdapter<>(getActivity(), myList)
        for (String s: myList){
            File f = new File(s);
            if (f.exists()) {
                myAdapter.remove(s);
                f.delete();
            }
        }
        listener.onDelete();

    }
    private void playAudio(String search){
        if(player.isPlaying()) {
            player.stop();
            player.reset();
        }

        try {
            player.setDataSource(search);
            player.prepare();
            player.start();
        }
        catch (Exception e){
            Log.e("Play audio error", e.toString());
        }

    }

      /*  try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(SearchManager.QUERY, search);
            startActivity(intent);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        }

       */



        /*player = new MediaPlayer();
        try{
            player.setDataSource(search);
            player.prepare();
            Toast.makeText(getContext(),"playing: " + search, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        player.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stopAudio();
            }
        });

    }

    private void stopAudio(){
        if (player != null){
            player.stop();
            player.release();
            Toast.makeText(getContext(),"stopped.", Toast.LENGTH_SHORT).show();

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playAudio(myList.get(i));
            }
        });

    }

         */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RecordingFragment.RecordingListener) {
            listener =  (AudioListFragment.AudioListListener) context;

        }

        else{
            throw new RuntimeException(context.toString()
                    + "must implement AudioListListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if (b != null){
            myList = b.getStringArrayList("files");
        }
    }
}
