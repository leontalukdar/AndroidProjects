package com.beginners.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    String mWord;
    int mLetterFailed = 0;
    String wrongLetters = "";
    int mGuessedLetters = 0;
    int mPoints = 0;
    int[] check = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRandomWord();
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
            mPoints++;
            clearScreen();
            setRandomWord();
            initializeCheck();
        }
    }

    public void initializeCheck(){
        for (int i=0;i<4;i++){
            check[i]=0;
        }
    }

    public void setRandomWord(){

        String wordList = "able acid aged also area army away baby back ball band bank base bath bear beat been beer bell belt best bill bird blow blue boat body bomb bond bone book boom born boss both bowl bulk burn bush busy call calm came camp card care case cash cast cell chat chip city club coal coat code cold come cook cool cope copy core cost crew crop dark data date dawn days dead deal dean dear debt deep deny desk dial dick diet disc disk does done door dose down draw drew drop drug dual duke dust duty each earn ease east easy edge else even ever evil exit face fact fail fair fall farm fast fate fear feed feel feet fell felt file fill film find fine fire firm fish five flat flow food foot ford form fort four free from fuel full fund gain game gate gave gear gene gift girl give glad goal goes gold golf gone good gray grew grey grow gulf hair half hall hand hang hard harm hate have head hear heat held hell help here hero high hill hire hold hole holy home hope host hour huge hung hunt hurt idea inch into iron item jack jane jean john join jump jury just keen keep kent kept kick kill kind king knee knew know lack lady laid lake land lane last late lead left less life lift like line link list live load loan lock logo long look lord lose loss lost love luck made mail main make male many mark mass matt meal mean meat meet menu mere mike mile milk mill mind mine miss mode mood moon more most move much must name navy near neck need news next nice nick nine none nose note okay once only onto open oral over pace pack page paid pain pair palm park part pass past path peak pick pink pipe plan play plot plug plus poll pool poor port post pull pure push race rail rain rank rare rate read real rear rely rent rest rice rich ride ring rise risk road rock role roll roof room root rose rule rush ruth safe said sake sale salt same sand save seat seed seek seem seen self sell send sent sept ship shop shot show shut sick side sign site size skin slip slow snow soft soil sold sole some song soon sort soul spot star stay step stop such suit sure take tale talk tall tank tape task team tech tell tend term test text than that them then they thin this thus till time tiny told toll tone tony took tool tour town tree trip true tune turn twin type unit upon used user vary vast very vice view vote wage wait wake walk wall want ward warm wash wave ways weak wear week well went were west what when whom wide wife wild will wind wine wing wire wise wish with wood word wore work yard yeah year your zero zone";
        String[] arrayWordList = wordList.split(" ");
        Log.d("MYLOG", "The number of words "+ arrayWordList.length);

        int randomWord = (int) (Math.random() * arrayWordList.length);
        mWord = arrayWordList[randomWord];

    }


    public  void  clearScreen(){

        TextView textView = (TextView) findViewById(R.id.wrongLetters);
        textView.setText("");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLetters);
        for (int i=0; i<linearLayout.getChildCount(); i++){
            textView = (TextView) linearLayout.getChildAt(i);
            textView.setText("_");
        }


        mGuessedLetters=0;
        mLetterFailed=0;
        wrongLetters = "";

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.hangman_0);

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
            Intent gameOverIntent = new Intent(this,GameOverActivity.class);

            gameOverIntent.putExtra("GAME_POINTS", mPoints);

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












