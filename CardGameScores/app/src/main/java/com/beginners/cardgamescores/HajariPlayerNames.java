package com.beginners.cardgamescores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HajariPlayerNames extends AppCompatActivity {

    String[] mPlayers = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajari_player_names);
    }

    public void hajariScoreActivity(View v){

        TextView firstPlayerText = (TextView) findViewById(R.id.firstPlayer);
        TextView secondPlayerText = (TextView) findViewById(R.id.secondPlayer);
        TextView thirdPlayerText = (TextView) findViewById(R.id.thirdPlayer);
        TextView fourthPlayerText = (TextView) findViewById(R.id.fourthPlayer);

        mPlayers[0] = firstPlayerText.getText().toString().toUpperCase();
        mPlayers[1] = secondPlayerText.getText().toString().toUpperCase();
        mPlayers[2] = thirdPlayerText.getText().toString().toUpperCase();
        mPlayers[3] = fourthPlayerText.getText().toString().toUpperCase();

        if (isValid()){

            Intent intent = new Intent(this,HajariScores.class);

            intent.putExtra("PLAYERS",mPlayers);

            startActivity(intent);
            finish();

        }
        else {
            Toast.makeText(getApplicationContext(), "Player name cannot be empty!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isValid(){

        boolean valid = true;
        for (int i=0;i<4;i++){

            if (mPlayers[i].length()==0){
                valid=false;
                break;
            }

        }
        return valid;
    }

}
