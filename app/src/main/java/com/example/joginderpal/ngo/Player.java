package com.example.joginderpal.ngo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by joginderpal on 03-12-2016.
 */
public class Player extends Activity implements View.OnClickListener , SensorEventListener {
    MediaPlayer mp,mp1;
    ArrayList<File> mysongs;
    SeekBar sb;
    public static Boolean s=true;
    Sensor msensor;
    SensorManager sm;
    ImageButton f,r,play;
    int position,position1;
    Thread updateseeker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerone);
        f= (ImageButton) findViewById(R.id.fone);
        r= (ImageButton) findViewById(R.id.rone);
        sb= (SeekBar) findViewById(R.id.seekbarone);
        play= (ImageButton) findViewById(R.id.playone);
       sm= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        msensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        f.setOnClickListener(this);
        r.setOnClickListener(this);
        play.setOnClickListener(this);
       updateseeker=new Thread(){
           @Override
           public void run() {
               int totalDuration=mp.getDuration();
               int currentpos=0;
               sb.setMax(totalDuration);
               while(currentpos<totalDuration){
                   try {
                       sleep(500);
                       currentpos=mp.getCurrentPosition();
                       sb.setProgress(currentpos);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               // super.run();
           }
       };

        Intent i=getIntent();
        Bundle b=i.getExtras();
        mysongs=(ArrayList) b.getParcelableArrayList("songlist");
         position=b.getInt("pos",0);

        Uri u=Uri.parse(mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        updateseeker.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               mp.seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.play:
                if (mp.isPlaying()){
                    mp.pause();

                }
                else{
                    mp.start();

                }
                break;

            case R.id.f:
                mp.stop();
                mp.release();
                position=(position+1)%mysongs.size();
                Uri u=Uri.parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
            case R.id.r:
                mp.stop();
                mp.release();
                position=(position-1<0)?mysongs.size()-1:position-1;
                Uri u1=Uri.parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u1);
                mp.start();
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       // sm.unregisterListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
      //  position1=position;
       //sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
         if (sensorEvent.values[0] == 3.0) {
             if (sensorEvent.values[0] == 1.0||(sensorEvent.values[0] == 1.0&&sensorEvent.values[0] == 3.0)) {

                 if (mp.isPlaying()) {
                     mp.pause();

                 } else {
                     mp.start();
                 }
             } else {

                 mp.stop();
                 mp.release();
                 position = (position + 1) % mysongs.size();
                 Uri u = Uri.parse(mysongs.get(position).toString());
                 mp = MediaPlayer.create(getApplicationContext(), u);
                 mp.start();

             }

         }
     }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sm.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        sm.unregisterListener(this);
    }
}
