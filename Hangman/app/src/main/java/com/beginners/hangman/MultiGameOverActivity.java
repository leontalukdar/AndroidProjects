package com.beginners.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MultiGameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game_over);

        MultiplayerActivity.fa.finish();

        String winner = getIntent().getStringExtra("WINNER_NAME");

        TextView textView = (TextView) findViewById(R.id.winner);
        textView.setText("WINNER : "+winner.toUpperCase());

    }
}
