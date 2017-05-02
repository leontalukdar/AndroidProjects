package com.beginners.hangman;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MultiplayerActivity extends AppCompatActivity {

    public static Activity fa;
    int cnt=0;
    TextView textView;
    String[] player = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        fa = this;

        player = getIntent().getStringArrayExtra("PLAYERS");

        textView = (TextView) findViewById(R.id.textViewTurn);
        textView.setText(player[cnt] + "'s TURN");

    }

    public void setWord(View v){

        cnt++;
        cnt = cnt%2;

        int t=cnt+1;
        t=t%2;

        textView.setText(player[cnt] + "'s TURN");

        Intent myIntent = new Intent(this,GameMultiActivity.class);

        TextView myText = (TextView) findViewById(R.id.eidtTextWord);
        String temp = myText.getText().toString();
        String[] combine = new String[2];
        combine[0]=temp;
        combine[1]=player[t];
        myIntent.putExtra("TARGET",combine);
        myText.setText("");

        startActivity(myIntent);
    }

}
