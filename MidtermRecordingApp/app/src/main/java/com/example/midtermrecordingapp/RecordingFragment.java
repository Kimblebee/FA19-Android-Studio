package com.example.midtermrecordingapp;


import android.content.Context;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Parcel;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordingFragment extends Fragment {
    // buttons
    private RecordingListener listener;
    private FloatingActionButton recFab;
    private FloatingActionButton stopFab;
    private FloatingActionButton saveFab;
    private FloatingActionButton discardFab;
    private Button viewSaved;


    // text
    private EditText recordName;
    private TextView recordStatus;

    //timer
    private Chronometer timekeeper;

    private MediaRecorder mediaRecorder;
    private File folder;
    private File filename;

    // int used to accordingly change file recording name
    int size = 1;


    public RecordingFragment() {
        // Required empty public constructor

    }

    public interface RecordingListener{
        void onSave(File f);
        void viewSaved();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_recording, container, false);
        recFab = v.findViewById(R.id.recordFab);
        saveFab = v.findViewById(R.id.saveFab);
        discardFab = v.findViewById(R.id.deleteFab);
        stopFab = v.findViewById(R.id.stopFab);
        viewSaved = v.findViewById(R.id.saveRecBtn);

        recordName = v.findViewById(R.id.editTitle);
        recordStatus = v.findViewById(R.id.recordStatus);

        timekeeper = v.findViewById(R.id.chrono);

        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myAudio");

                //getActivity().getExternalFilesDir(null) + "/myAudio/");



        setupMediaRecorder();
        setFabs();

        reset();

        return v;
    }

    private void startRecording(){


        if (!folder.exists()){
            // if the folder doesnt exist, create it
            folder.mkdir();
            //Toast.makeText(getContext(), folder.toString(), Toast.LENGTH_LONG).show();
        }



        filename = new File(folder.toString() + "/recording" + size +".mp3");
        //setupMediaRecorder();

        try{
            mediaRecorder.setOutputFile(filename);
            mediaRecorder.prepare();
            mediaRecorder.start();

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }


    private void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.reset();


    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(filename);

    }


    // setting the onclick listeners for the buttons
    private void  setFabs(){
        recFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                start();
            }
        });

        stopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                stop();
            }
        });

        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                save(view);
            }
        });

        discardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                discard(view);
            }
        });

        viewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Handle the click.
                listener.viewSaved();
            }
        });





    }

    private void start(){

        // hide the recording button, disable the view recordings, show the stop button
        recFab.hide();
        viewSaved.setEnabled(false);
        stopFab.show();

        // make sure the timer is reset and start it
        timekeeper.setBase(SystemClock.elapsedRealtime());
        timekeeper.start();

        // change the status to show that it is recording
        recordStatus.setText("Recording...");

        startRecording();


        /*
        getActivity().startService(intent);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
         */



    }

    //
    private void stop(){
        //hide stop button, show buttons to delete or save recording
        stopFab.hide();
        saveFab.show();
        discardFab.show();

       // stop the timer
        timekeeper.stop();


        recordStatus.setText("Save Recording?");

        // enable name editing
        recordName.setEnabled(true);
        stopRecording();

    }

    private void save(View v){
        Snackbar.make(v, "Saved audio  \"" + recordName.getText().toString() + "\"", Snackbar.LENGTH_LONG).show();
        // if the new audio name isnt the same, then rename the file
        if (!recordName.getText().toString().equals("recording" + size))
            filename.renameTo(new File (folder.toString() + "/" + recordName.getText().toString().replaceAll(" ", "_") + ".mp3"));

        /*
               Bundle b = new Bundle();
        AudioItem a = new AudioItem(timekeeper.toString(), recordName.getText().toString(), filename);
        b.putParcelable(recordName.getText().toString(), a);
         */

        listener.onSave(filename);
        size++;


        reset();
    }

    // button action for disc
    private void discard(View v){
        Snackbar.make(v, "Discarded recording  \"" + filename.toString(), Snackbar.LENGTH_LONG).show();
        filename.delete();


        reset();
    }

    // this resets the view to get ready to record fresh audio
    private void reset(){
        // hide buttons
        stopFab.hide();
        saveFab.hide();
        discardFab.hide();

        // only show button to record a new audio
        recFab.show();

        // reset text for input and display
        recordStatus.setText("Start Recording?");
        recordName.setText("Recording" + size);

        // you are now able to view your saved audio
        viewSaved.setEnabled(true);

        // you can no longer change the title of the recording
        recordName.setEnabled(false);

        timekeeper.setBase(SystemClock.elapsedRealtime());


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RecordingFragment.RecordingListener) {
            listener =  (RecordingFragment.RecordingListener) context;

        }

        else{
            throw new RuntimeException(context.toString()
                    + "must implement RecordingListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if (b != null){
            size = b.getInt("size");
        }
    }
}
