package com.examples.hw14_downs;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    MainView v = null;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        v = new MainView(this);
        setContentView(v);
    }
    @Override
    protected void onPause() {
        if (mp != null) {
            mp.pause();
            mp.release();
            mp = null;
        }
        super.onPause();
        if (v != null)
            v.pause();
    }
    @Override
    protected void onResume() {
        mp = null;
        mp = MediaPlayer.create(this, R.raw.forest);
        if(mp != null) {
            mp.setLooping(true);
            mp.start();
        }
        super.onResume();
        if (v != null)
            v.resume();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Leaving Game")
                .setMessage("Are you sure you want to return to the title screen?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false)
                .show();
    }

}
