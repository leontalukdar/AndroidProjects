package com.beginners.collectcandy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private int curScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageButton imageButton = (ImageButton) findViewById(R.id.playAgain);
        imageButton.setImageResource(R.drawable.play);

        TextView scoreLabel = (TextView) findViewById(R.id.score);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScore);

        curScore = getIntent().getIntExtra("CURSCORE",0);

        scoreLabel.setText("SCORE : "+curScore);

        SharedPreferences preferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = preferences.getInt("HIGH_SCORE",0);

        if (curScore>highScore)
            highScore=curScore;
        highScoreLabel.setText("High Score : "+highScore);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("HIGH_SCORE",highScore);
        editor.commit();

    }

    public void playAgain(View v)
    {
        Intent intent = new Intent(this,StartGame.class);
        startActivity(intent);
        finish();
    }
}
