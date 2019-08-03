package com.example.dellpc.fyp1;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

import java.util.Timer;

public class AlarmActivity extends AppCompatActivity {
    Timer timer; public static  MediaPlayer alarm,alarm1,alarm2; int abc; SeekBar mediaPlayer ;AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Alarm Settings");
        setSupportActionBar(toolbar);
        mediaPlayer = (SeekBar)findViewById(R.id.seekBar2);



        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        mediaPlayer.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));









        mediaPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        alarm = MediaPlayer.create(this, R.raw.alarm);alarm1 = MediaPlayer.create(this, R.raw.alarm1);alarm2= MediaPlayer.create(this, R.raw.alarm2);
        Button backfrominst = (Button) findViewById(R.id.button5);

        backfrominst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(abc==1)
                {setResult( Constants.RESULT_CODE_a );
                    finish();
                }
                else if(abc==2)
                {setResult( Constants.RESULT_CODE_b );
                    finish();
                }
                else
                {setResult( Constants.RESULT_CODE_c );
                    finish();
                }

            }
        });



    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.a:
                if (checked)
                    abc=1;
                press(1);
                break;
            case R.id.b:
                if (checked)
                    abc=2;
                press(2);
                break;
            case R.id.c:
                if (checked)
                    abc=3;
                press(3);
                break;
        }
    }
    public void press(final int i){
        if(i==1)
        {
            alarm.start();}
        else if(i==2)
        {
            alarm1.start();}
        else
        {
            alarm2.start();}



        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        View mview =getLayoutInflater().inflate(R.layout.customdialog,null);


        mbuilder.setView(mview);
        final AlertDialog dialog= mbuilder.create();
        dialog.show();
        //timer
        timer= new Timer();




        Button stopbtn=(Button) mview.findViewById(R.id.button);

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==1)
                {
                    alarm.pause();}
                else if(i==2)
                {
                    alarm1.pause();}
                else
                {
                    alarm2.pause();}

                dialog.dismiss();
                timer.cancel();


            }
        });


    }
}

