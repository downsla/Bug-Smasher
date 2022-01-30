package com.examples.hw14_downs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button scoresButton = (Button) findViewById(R.id.scoresButton);
        Button gameButton = (Button) findViewById(R.id.gameButton);

        assert scoresButton != null;
        scoresButton.setOnClickListener(this);
        assert gameButton != null;
        gameButton.setOnClickListener(this);



        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Assets.high_score = prefs.getInt("key_high_score", 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.scoresButton) {
            Intent intent = new Intent(StartActivity.this, PrefsActivity.class);
            startActivity(intent);
        } else if(id == R.id.gameButton) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            StartActivity.this.startActivity(intent);
        }
    }
}