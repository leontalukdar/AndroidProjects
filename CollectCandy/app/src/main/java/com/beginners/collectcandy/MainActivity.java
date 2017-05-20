package com.beginners.collectcandy;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startGame;
    private ImageView box;
    private ImageView black;
    private ImageView orange;
    private ImageView pink;
    private ImageView green;

    private int boxY;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean actionFlag = false;
    private boolean firstTouch = false;

    private int frameHeight;
    private int boxHeight;
    private int boxWidth;

    private int screenHeight;
    private int screenWidth;

    private int orangeX;
    private int orangeY;
    private int greenX;
    private int greenY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;

    private Random random = new Random();

    private int score=0;

    private SoundPlayer soundPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startGame = (TextView) findViewById(R.id.startGame);
        box = (ImageView) findViewById(R.id.box);
        black = (ImageView) findViewById(R.id.black);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);
        green = (ImageView) findViewById(R.id.green);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenHeight = size.y;
        screenWidth = size.x;

        black.setX(-80);
        black.setY(-80);
        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        green.setX(-80);
        green.setY(-80);

        scoreLabel.setText("SCORE : 0");

    }

    public void changePos()
    {
        hitCheck();

        orangeX -= 18;
        if (orangeX<0)
        {
            orangeX = screenWidth + 20;
            orangeY =(int) (random.nextDouble()*(screenHeight-orange.getHeight()));
        }

        orange.setX(orangeX);
        orange.setY(orangeY);

        blackX -= 16;
        if (blackX<0)
        {
            blackX = screenWidth + 20;
            blackY =(int) (random.nextDouble()*(screenHeight-black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        pinkX -= 24;
        if (pinkX<0)
        {
            pinkX = screenWidth + 20;
            pinkY =(int) (random.nextDouble()*(screenHeight-pink.getHeight()));
        }

        pink.setX(pinkX);
        pink.setY(pinkY);

        greenX -= 12;
        if (greenX<0)
        {
            greenX = screenWidth + 20;
            greenY =(int) (random.nextDouble()*(screenHeight-green.getHeight()));
        }

        green.setX(greenX);
        green.setY(greenY);

        if (actionFlag)
            boxY -= 20;
        else
            boxY += 20;
        if (boxY<0)
            boxY = 0;
        if (boxY>(frameHeight-boxHeight))
            boxY = frameHeight-boxHeight;

        box.setY(boxY);

        scoreLabel.setText("SCORE : "+score);
    }

    public void hitCheck()
    {
        int orangeCenterX = orangeX + orange.getWidth()/2;
        int orangeCenterY = orangeY + orange.getHeight()/2;

        if (0<=orangeCenterX && orangeCenterX<=boxWidth && boxY<=orangeCenterY && orangeCenterY<=boxY+boxHeight)
        {
            score += 20;
            orangeX = -10;
            soundPlayer.playHitSound();
        }

        int greenCenterX = greenX + green.getWidth()/2;
        int greenCenterY = greenY + green.getHeight()/2;

        if (0<=greenCenterX && greenCenterX<=boxWidth && boxY<=greenCenterY && greenCenterY<=boxY+boxHeight)
        {
            score += 10;
            greenX = -10;
            soundPlayer.playHitSound();
        }

        int pinkCenterX = pinkX + pink.getWidth()/2;
        int pinkCenterY = pinkY + pink.getHeight()/2;

        if (0<=pinkCenterX && pinkCenterX<=boxWidth && boxY<=pinkCenterY && pinkCenterY<=boxY+boxHeight)
        {
            score += 30;
            pinkX = -10;
            soundPlayer.playHitSound();
        }

        int blackCenterX = blackX + black.getWidth()/2;
        int blackCenterY = blackY + black.getHeight()/2;

        if (0<=blackCenterX && blackCenterX<=boxWidth && boxY<=blackCenterY && blackCenterY<=boxY+boxHeight)
        {
            soundPlayer.playOverSound();
            timer.cancel();
            timer = null;

            Intent intent = new Intent(this,Result.class);

            intent.putExtra("CURSCORE",score);

            startActivity(intent);
            finish();

        }

    }

    public boolean onTouchEvent(MotionEvent touch)
    {
        if (firstTouch==false)
        {
            firstTouch = true;

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frameLayout.getHeight();
            boxY = (int) box.getY();
            boxHeight = box.getHeight();
            boxWidth = box.getWidth();

            startGame.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
        else
        {
            if (touch.getAction() == MotionEvent.ACTION_DOWN) {
                actionFlag = true;
            } else if (touch.getAction() == MotionEvent.ACTION_UP) {
                actionFlag = false;
            }
        }

        return true;
    }

}
