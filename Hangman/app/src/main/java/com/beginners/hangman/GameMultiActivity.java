package com.beginners.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameMultiActivity extends AppCompatActivity {

    String mWord;
    int mLetterFailed = 0;
    String wrongLetters = "";
    int mGuessedLetters = 0;
    int mPoints = 0;
    int[] check = new int[4];
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        String[] word = getIntent().getStringArrayExtra("TARGET");
        winner = word[1];
        Log.d("MYLOG","The word is "+word[0]);

        mWord = word[0];
        createTextView(word[0]);
        initializeCheck();
    }

    /**
     * Retrieving the letter introduce in the editText
     * @param v from the button check.
     */
    public void  introduceLetter(View v){
        EditText myEditText = (EditText) findViewById(R.id.editTextLetter);
        String myString = myEditText.getText().toString();
        myEditText.setText("");
        Log.d("MYLOG","The letter is "+myString);

        if(myString.length()==1){
            checkLetter(myString.toLowerCase());
        }
        else {
            Toast.makeText(this,"Please enter a letter",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checking if the letter is matching with any word's letter.
     * @param letter letter entered by user.
     */
    public  void checkLetter(String letter){

        boolean letterGuessed = false;
        char letterFromText = letter.charAt(0);
        for (int i=0; i<mWord.length();i++){
            char letterFromWord = mWord.charAt(i);
            if (letterFromText==letterFromWord && check[i]==0){
                Log.d("MYLOG", "There is match.");
                letterGuessed = true;
                showLettersAtIndex(i,letterFromText);
                mGuessedLetters++;
                check[i]=1;
            }
        }

        if (!letterGuessed){
            showLetterFailed(letterFromText);
            letterFailed();
        }

        if (mGuessedLetters == mWord.length()){
            finish();
        }
    }

    public void createTextView(String word){

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLetters);

        for (int i=0; i<word.length(); i++){
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.dynamic_text_view,null);
            linearLayout.addView(textView);
        }

    }

    public void initializeCheck(){
        for (int i=0;i<4;i++){
            check[i]=0;
        }
    }


    public void  showLetterFailed(char wrongLetter){

        String temp = Character.toString(wrongLetter);
        wrongLetters+=temp;
        TextView textView = (TextView) findViewById(R.id.wrongLetters);
        textView.setText(wrongLetters);

    }


    public void  letterFailed(){

        mLetterFailed++;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if (mLetterFailed == 1){
            imageView.setImageResource(R.drawable.hangman_1);
        }
        else  if (mLetterFailed == 2){
            imageView.setImageResource(R.drawable.hangman_2);
        }
        else  if (mLetterFailed == 3){
            imageView.setImageResource(R.drawable.hangman_3);
        }
        else  if (mLetterFailed == 4){
            imageView.setImageResource(R.drawable.hangman_4);
        }
        else  if (mLetterFailed == 5){
            imageView.setImageResource(R.drawable.hangman_5);
        }
        else  if (mLetterFailed == 6){
            imageView.setImageResource(R.drawable.hangman_6);
        }
        else if (mLetterFailed == 7){

            Intent gameOverIntent = new Intent(this,MultiGameOverActivity.class);

            gameOverIntent.putExtra("WINNER_NAME", winner);

            startActivity(gameOverIntent);
            finish();
        }
    }


    /**
     * Displaying a letter guessed by the user.
     * @param position of the letter.
     * @param letter (letter guessed)
     */
    public void showLettersAtIndex(int position, char letter){

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLetters);
        TextView textView = (TextView) linearLayout.getChildAt(position);
        textView.setText(Character.toString(letter));

    }
}












