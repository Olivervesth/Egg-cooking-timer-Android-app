package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
private CountDownTimer eggTimer;
    long countdownvalue;
    long lasttimervalue;//Last known timer value
    boolean iscounterstarted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Starts the countdown
    public void countdown_start(){
        long countvalue = 0;
        if (lasttimervalue != 0){
            countvalue = lasttimervalue;
        }else if (lasttimervalue == 0){
            if (iscounterstarted){
                countdown_stop();
                timerBtn_state(false);
            }
            countvalue = countdownvalue;
        }

        if (countvalue != 0){
            //Starting a countdowntimer it runs threaded so you can risk starting multiple if not closed
            eggTimer = new CountDownTimer( countvalue, 1000) {
                EditText counter = findViewById(R.id.timerelement);
                @Override
                public void onTick(long millisUntilFinished) {
                    DateFormat simple = new SimpleDateFormat("mm:ss");
                    Date result = new Date(millisUntilFinished);
                    counter.setText(simple.format(result));
                    lasttimervalue = millisUntilFinished;
                }
                @Override
                public void onFinish() {
                    timerBtn_state(false);
                    counter.setText("done!");
                }
            }.start();

        }
    }
    //stops the countdown
    public void countdown_stop(){
        eggTimer.cancel();
    }
    public void timerBtn(View view){
        if (iscounterstarted){
            timerBtn_state(false);
        }else{
            timerBtn_state(true);
        }

    }
    //Changes the ui state of the button
    public void timerBtn_state(boolean state){
        Button button = findViewById(R.id.timer_btn);
        if (state){
            countdown_start();
            button.setText("Stop");
            iscounterstarted = true;
        }else if (!state){
            countdown_stop();
            button.setText("Start");
            iscounterstarted = false;
        }
    }
    // Setting the time which the countdown should start on
    public void setTimerValue(View view){
        EditText counter = findViewById(R.id.timerelement);
        DateFormat simple = new SimpleDateFormat("mm:ss");
        if (iscounterstarted){
            timerBtn_state(false);
        }
        switch (view.getId()) {
            case R.id.softBoiledButton:
                counter.setText(simple.format(new Date(countdownvalue)));
                countdownvalue = 300000;
                lasttimervalue = 0;
                counter.setText(simple.format(new Date(countdownvalue)));
                startBtn(true);

                break;
            case R.id.mediumBoiledButton:
                countdownvalue = 420000;
                lasttimervalue = 0;
                counter.setText(simple.format(new Date(countdownvalue)));
                startBtn(true);
                break;
            case R.id.hardBoiledButton:
                countdownvalue = 600000;
                lasttimervalue = 0;
                counter.setText(simple.format(new Date(countdownvalue)));
                startBtn(true);
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }
    private void startBtn(boolean status){
        findViewById(R.id.timer_btn).setEnabled(status); //The start button is now pressable
    }

}