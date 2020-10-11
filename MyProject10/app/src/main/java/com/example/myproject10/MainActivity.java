package com.example.myproject10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener{

    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout bottomSheet;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private static int PERMISSION_REQUEST_CODE;
    private ArrayList<String> audioNames;
    private ArrayList<String> audioFiles;

    private TextView bottomSheetTV;

    private Switch shuffleSwitch;

    private MediaPlayer mp;
    private SeekBar seekBar;
    private Handler handler;
    private Runnable runnable;

    private Button playBtn;
    private Button pauseBtn;

    private int currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();


        recyclerView = findViewById(R.id.rv_musicList);
        shuffleSwitch = findViewById(R.id.switch1);
        bottomSheetTV = findViewById(R.id.tv_songName);
        bottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        seekBar = findViewById(R.id.seekBar);


        bottomSheetTV.setClickable(true);
        bottomSheetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "REEEEEEEE", Toast.LENGTH_LONG).show();

            }
        });

        setButtons();
        setMediaPlayer();


        audioNames = new ArrayList<>();
        audioFiles = new ArrayList<>();

        // sets up audio and audio adapter
        getAudio();
        mAdapter = new MyAdapter(this, audioNames);
        mAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }


    // sets the play/pause button
    void setButtons(){
        playBtn = findViewById(R.id.btn_play);
        pauseBtn = findViewById(R.id.btn_pause);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeAudio();
            }
        });


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAudio();
            }
        });

        playBtn.setEnabled(false);
        pauseBtn.setEnabled(false);


    }

    //initializes the media player, seekbar, and its handler for the seekbar
    void setMediaPlayer(){
        mp = new MediaPlayer();
        handler = new Handler();
        setSeekBar();
    }

    //binding the seekbar to the media player
    void setSeekBar(){
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mp.getDuration());
                mp.start();
                changeSeekbar();

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    mp.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // changes the seekbar's position based on the audio progression
    private void changeSeekbar() {
        try {
            seekBar.setProgress(mp.getCurrentPosition());

            if (mp.isPlaying()) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekbar();
                    }
                };
                handler.postDelayed(runnable, 10);
            }
        }
        catch (Exception e){
            Log.e("SEEKBAR: ", e.toString());
        }
    }

    // gets all audio from the device
    public List<File> getAudio(){
        ArrayList<File> files = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE};

        Cursor c =  getApplicationContext().getContentResolver().query(uri, projection, null,null, null);

        if (c != null){
            Log.v("NUMBER OF FILES: ", c.getCount() +"");
            while (c.moveToNext()){
                String path = c.getString(0);
                String name = c.getString(1);

                audioNames.add(name);
                audioFiles.add(path);
                Log.e("Name :" + name, " Path :" + path);
            }
        }

        c.close();

        return files;
    }


    // play a fresh audio clicked from the list
    public void playAudio(int i){
        currentSong = i;
        try{
            if(mp.isPlaying()) {
                mp.stop();
                mp.reset();
            }

            mp.setDataSource(audioFiles.get(i));
            mp.prepare();
            mp.start();

            bottomSheetTV.setText("Now Playing: " + audioNames.get(i));




            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mp.stop();
                    mp.reset();
                    if (shuffleSwitch.isChecked()){
                        Random rand = new Random();
                        playAudio(rand.nextInt(audioFiles.size()));
                    }

                    else if (currentSong == audioFiles.size() - 1)
                        playAudio(0);

                    else
                        playAudio(currentSong + 1);

                    //Toast.makeText(getApplicationContext(), shuffleSwitch.isChecked() + "", Toast.LENGTH_LONG).show();




                }
            });
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e("ERROR LISTENER: ", mp.toString());
                    return false;
                }
            });

            playBtn.setEnabled(true);
            pauseBtn.setEnabled(true);
        }

        catch (Exception e){
            e.printStackTrace();
            Log.e("PLAY AUDIO ERROR", e.toString());
        }

    }

    // continue playing current audio
    public void resumeAudio(){
        mp.start();
        changeSeekbar();
    }

    //pause a currently played audio
    public void pauseAudio(){
        mp.pause();
    }


    // request permission for reading external storage
    void requestPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    // onpermissionrequestPermissionresult


    // I TRIED TO DO THIS SO THAT IT WOULD AUTOMATICALLY UPDATE, BUT IT WOULDNT EVER CALL THIS AND IDK Y
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission already granted", Toast.LENGTH_LONG).show();
                    } else {
                       // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                }
            }
        }
    }



    @Override
    public void onItemClick(View view, int position) {
        playAudio(position);

    }
}
