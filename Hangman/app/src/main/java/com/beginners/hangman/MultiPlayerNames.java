package com.beginners.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MultiPlayerNames extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_names);
    }

    public void  namesOK(View v){

        TextView firstPlayerText = (TextView) findViewById(R.id.editTextFirstPlayer);
        TextView secondPlayerText = (TextView) findViewById(R.id.editTextSecondPlayer);

        String[] player = new String[2];
        String firstPlayer = firstPlayerText.getText().toString();
        String secondPlayer = secondPlayerText.getText().toString();
        player[0]=firstPlayer;
        player[1]=secondPlayer;
        if(player[0].length()==0 || player[1].length()==0){
            Toast.makeText(this,"Player names cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this,MultiplayerActivity.class);

            intent.putExtra("PLAYERS",player);

            startActivity(intent);
            finish();
        }

    }

}
