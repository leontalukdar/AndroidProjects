package com.beginners.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences preferences = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);

        String scores = preferences.getString("SCORES","EMPTY");

        TextView textView = (TextView) findViewById(R.id.scoreTextView);
        textView.setText(scores.toUpperCase());

    }

    public void resetScore(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);

        builder.setIcon(android.R.drawable.sym_def_app_icon);
        builder.setTitle("Erasing Scores");
        builder.setMessage("Erasing scores can not be undone!!!");
        builder.setCancelable(false);
        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("ERASE", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView textView = (TextView) findViewById(R.id.scoreTextView);
                textView.setText("EMPTY");
                SharedPreferences preferences = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
