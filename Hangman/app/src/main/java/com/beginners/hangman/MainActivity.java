package com.beginners.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void startSinglePlayer(View v){
        Intent myIntent = new Intent(this,GameActivity.class);
        startActivity(myIntent);
    }

    public void startMultiPlayer(View v){
        Intent myIntent = new Intent(this,MultiPlayerNames.class);
        startActivity(myIntent);
    }

    public void openScore(View v){
        Intent myIntent = new Intent(this,ScoreActivity.class);
        startActivity(myIntent);
    }

}
