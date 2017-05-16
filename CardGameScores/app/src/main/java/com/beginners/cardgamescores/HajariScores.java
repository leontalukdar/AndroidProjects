package com.beginners.cardgamescores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HajariScores extends AppCompatActivity {

    String[] mPlayers = new String[10];
    TableRow mPlayerScores;
    String[][] mPlayerCurScore = new String[5][1000];
    String[] mGetPlayersScore = new String[10];
    TextView[] mPlayerMatchScore;
    int mNumberOfRounds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajari_scores);

        mPlayers = getIntent().getStringArrayExtra("PLAYERS");
        Log.d("Players are ",mPlayers[0]+" "+mPlayers[1]+" "+mPlayers[2]+" "+mPlayers[3]);

        findViewById(R.id.tapListener).setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(HajariScores.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("TEST", "onDoubleTap");
                    checkForFourthPlayerScore();
                    return super.onDoubleTap(e);
                }
            });


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        initialize();
        initializeScroll();

    }

    public void initialize() {

        TableLayout playerLayout = (TableLayout) findViewById(R.id.playerTable);

        TableRow tr = new TableRow(this);
        tr.setPadding(5,5,5,5);

        for(int i=0;i<4;i++){

            TextView tx = new TextView(this);
            tx.setText(" "+mPlayers[i]+" ");
            tx.setTextSize(23);
            tx.setGravity(Gravity.CENTER);
            tr.addView(tx);

        }

        playerLayout.addView(tr);

        TableRow ntr = new TableRow(this);
        ntr.setPadding(5,5,5,5);

        for(int i=0;i<4;i++){

            TextView tx = new TextView(this);
            tx.setText("0");
            mPlayerCurScore[i][0]= "0";
            tx.setTextSize(25);
            tx.setGravity(Gravity.CENTER);
            ntr.addView(tx);

        }

        playerLayout.addView(ntr);

        mPlayerScores = (TableRow) playerLayout.getChildAt(1);

        initializeHints();

    }

    public void initializeHints(){

        TextView[] player = new TextView[10];
        player[0] = (TextView) findViewById(R.id.player1);
        player[1] = (TextView) findViewById(R.id.player2);
        player[2] = (TextView) findViewById(R.id.player3);
        player[3] = (TextView) findViewById(R.id.player4);

        for(int i=0; i<4;i++){
            player[i].setHint(mPlayers[i]);
        }
        mPlayerMatchScore = player;

    }

    public void initializeScroll(){
        TableLayout playerLayout = (TableLayout) findViewById(R.id.playerTableScroll);

        TableRow tr = new TableRow(this);
        tr.setPadding(5, 5, 5, 5);
        TextView tx = new TextView(this);
        tx.setText("Round No. ");
        tx.setTextSize(17);
        tx.setGravity(Gravity.CENTER);
        tr.addView(tx);
        for (int i = 0; i < 4; i++) {

            tx = new TextView(this);
            tx.setText(" " + mPlayers[i] + " ");
            tx.setTextSize(17);
            tx.setGravity(Gravity.CENTER);
            tr.addView(tx);

        }

        playerLayout.addView(tr);

    }


    public void updateScrollBar(View v){

        getPlayersScore();
        if (isValid()) {
            mNumberOfRounds++;
            TableLayout playerLayout = (TableLayout) findViewById(R.id.playerTableScroll);

            TableRow tr = new TableRow(this);
            tr.setPadding(5, 5, 5, 5);
            TextView tx = new TextView(this);
            tx.setText(" " + String.valueOf(mNumberOfRounds) + " ");
            tx.setTextSize(17);
            tx.setGravity(Gravity.CENTER);
            tr.addView(tx);
            for (int i = 0; i < 4; i++) {

                tx = new TextView(this);
                tx.setText(" " + mGetPlayersScore[i] + " ");
                mPlayerCurScore[i][mNumberOfRounds] = mGetPlayersScore[i];
                tx.setTextSize(17);
                tx.setGravity(Gravity.CENTER);
                tr.addView(tx);

            }

            playerLayout.addView(tr);
            updateFinalScore();
        }
        else {
            Toast.makeText(getApplicationContext(), "Calculation error on Invalid Characters", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isValid(){

        int flag=0;

        for (int i=0;i<4;i++){

            String t = mGetPlayersScore[i];

            for (int j=0;j<t.length();j++){
                if ((t.charAt(j)>='0' && t.charAt(j)<='9')||t.charAt(0)=='-')
                    flag=1;
                else {
                    return false;
                }
            }
        }

        int score = 0;
        for (int i=0;i<4;i++){
            score+=Integer.parseInt(mGetPlayersScore[i]);
        }
        if (score==360 && flag==1)
            return true;
        else
            return false;

    }

    public void getPlayersScore(){

        TextView tx1 = (TextView) findViewById(R.id.player1);
        TextView tx2 = (TextView) findViewById(R.id.player2);
        TextView tx3 = (TextView) findViewById(R.id.player3);
        TextView tx4 = (TextView) findViewById(R.id.player4);

        for (int i=0;i<4;i++){
            mGetPlayersScore[i]=mPlayerMatchScore[i].getText().toString();
        }


        fillScore();

       for (int i=0;i<4;i++)
           mPlayerMatchScore[i].setText("");

    }

    public void fillScore(){

        for (int i=0;i<4;i++){
            if (mGetPlayersScore[i].length()==0)
                mGetPlayersScore[i]="0";
        }

    }

    public void updateFinalScore(){

        int[] finalScore = new int[5];

        for(int i=0;i<4;i++){
            int score = 0;
            for (int j=0;j<=mNumberOfRounds;j++){
                score+= Integer.parseInt(mPlayerCurScore[i][j]);
            }

            finalScore[i]=score;
            TextView tx = (TextView) mPlayerScores.getChildAt(i);
            tx.setText(" "+String.valueOf(score)+" ");

        }

        checkForWinner(finalScore);

    }

    public void checkForWinner(int[] finalScore){

        int position = 0;
        int score=finalScore[0];
        for (int i=1;i<4;i++){

            if (finalScore[i]>score){
                score=finalScore[i];
                position=i;
            }

        }

        if (score>=1000){
            getWinner(position,score);
        }

    }


    public void getWinner(int pos,int score){

        Intent intent = new Intent(this,HajriWinner.class);

        intent.putExtra("WINNER",mPlayers[pos]);
        intent.putExtra("SCORE",score);

        startActivity(intent);
        finish();

    }


    public void checkForFourthPlayerScore(){

        String[] score = new String[10];

        for (int i=0;i<4;i++){
            score[i]=mPlayerMatchScore[i].getText().toString();
        }

        int empty=0;
        int pos=0;
        int cnt=0;
        for (int i=0;i<4;i++){

            if (score[i].length()==0){
                empty++;
                pos=i;
            }
            else {
                cnt+=Integer.parseInt(score[i]);
            }

        }

        cnt=360-cnt;

        if (empty==1){
            mPlayerMatchScore[pos].setText(String.valueOf(cnt));
        }
        else {
            Toast.makeText(getApplicationContext(), "Need three player's score!!", Toast.LENGTH_SHORT).show();
        }

    }


}
