package com.beginners.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    int mGamePoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int gamePoints = getIntent().getIntExtra("GAME_POINTS",0);

        TextView textView = (TextView) findViewById(R.id.textViewPoints);
        textView.setText(String.valueOf(gamePoints));

        mGamePoints = gamePoints;
    }

    public void saveScore(View v){

        SharedPreferences scores = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);

        TextView textName = (TextView) findViewById(R.id.editTextName);

        String name = textName.getText().toString();

        SharedPreferences.Editor editor = scores.edit();
        String previousScores = scores.getString("SCORES","");


        editor.putString("SCORES",name + " " + mGamePoints+" POINTS\n"+previousScores);
        editor.commit();
        finish();

    }

}
