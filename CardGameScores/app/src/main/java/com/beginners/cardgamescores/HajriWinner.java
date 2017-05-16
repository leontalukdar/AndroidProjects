package com.beginners.cardgamescores;

import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class HajriWinner extends AppCompatActivity {

    String mWinner;
    int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajri_winner);

        InputStream stream = null;
        try {
            stream = getAssets().open("piggy.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.winner);

        mWinner = getIntent().getStringExtra("WINNER");
        mScore = getIntent().getIntExtra("SCORE",0);

        winnerAnnounce();

    }


    public void winnerAnnounce(){

        TextView tx = (TextView) findViewById(R.id.winnerText);
        tx.setText("New Hajari ''"+mWinner+"'' \n"+"Your Score : "+String.valueOf(mScore));
        tx.setTextSize(30);
        tx.setTextColor(Color.rgb(255,0,0));

    }



}
