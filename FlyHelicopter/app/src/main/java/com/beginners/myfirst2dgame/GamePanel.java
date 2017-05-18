package com.beginners.myfirst2dgame;

/**
 * Created by LEON on 5/15/2017.
 */

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final float WIDTH = 1600;
    public static final float HEIGHT = 600;
    public static final int MOVESPEED = -10;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<SmokePuff> smokePuffs;
    private long smokeStartTime;
    private ArrayList<Missile> missiles;
    private long missileStartTime;
    private Random random = new Random();
    private ArrayList<TopBorder> topBorders;
    private ArrayList<BottomBorder> bottomBorders;
    private int maxBorderHeight;
    private int minBorderHeight;
    private boolean topDown = true;
    private boolean bottomDown = true;
    private boolean newGameCreated;
    private boolean firstTime = false;

    private int progressDemo = 20;

    private Explosion explosion;
    private long startReset;
    private boolean reset;
    private boolean started;
    private boolean dissappear;
    public static int mBest;

    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        //storeData.setScore(mBest);
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{
                thread.setRunning(false);
                thread.join();
                thread = null;
                retry = false;
            }
            catch(InterruptedException e){e.printStackTrace();}
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.test),180,95,7);

        topBorders = new ArrayList<TopBorder>();
        bottomBorders = new ArrayList<BottomBorder>();

        smokePuffs = new ArrayList<SmokePuff>();
        smokeStartTime = System.nanoTime();

        missiles = new ArrayList<Missile>();
        missileStartTime = System.nanoTime();

        //we can safely start the game loop
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (!player.getPlaying() && newGameCreated && reset){
                player.setPlaying(true);
                player.setUp(true);
            }
            if (player.getPlaying()){
                if (!started)
                    started=true;
                reset=false;
                player.setUp(true);
            }
            return true;
        }
        if (event.getAction()==MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update()
    {
        if (player.getPlaying()){

            if (topBorders.isEmpty())
            {
                player.setPlaying(false);
                return;
            }
            if (bottomBorders.isEmpty())
            {
                player.setPlaying(false);
                return;
            }

            player.update();
            bg.update();

            maxBorderHeight = 30+player.getScore()/progressDemo;
            if (maxBorderHeight>(int)(HEIGHT/4))
                maxBorderHeight = (int) HEIGHT/4;
            minBorderHeight = 5+player.getScore()/progressDemo;


            for (int i=0;i<topBorders.size();i++)
            {
                if (collision(topBorders.get(i),player))
                {
                    player.setPlaying(false);
                    this.findViewById(android.R.id.content).getRootView();
                }
            }
            for (int i=0;i<bottomBorders.size();i++)
            {
                if (collision(bottomBorders.get(i),player))
                    player.setPlaying(false);
            }

            this.updateTopBorder();
            this.updateBottomBorder();

            long missileElasped = (System.nanoTime()-missileStartTime)/1000000;
            if (missileElasped>(3000-player.getScore()/4))
            {
                if (missiles.size()==0)
                {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),getWidth()+10,(int) HEIGHT/2,124,31,player.getScore(),13));
                }
                else
                {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile), getWidth()+10,(int)(random.nextDouble()* HEIGHT-maxBorderHeight*2)+maxBorderHeight,124,31,player.getScore(),13));
                }
                missileStartTime = System.nanoTime();
            }

            for (int i=0;i<missiles.size();i++)
            {
                missiles.get(i).update();
                if (collision(missiles.get(i),player))
                {
                    missiles.remove(i);
                    player.setPlaying(false);
                    break;
                }
                if (missiles.get(i).getX()<-100)
                {
                    missiles.remove(i);
                    break;
                }
            }

            long elasped = (System.nanoTime()-smokeStartTime)/1000000;

            if (elasped>120)
            {
                smokePuffs.add(new SmokePuff(player.getX(),player.getY()+40));
                smokeStartTime = System.nanoTime();
            }

            for (int i=0;i<smokePuffs.size();i++)
            {
                smokePuffs.get(i).update();
                if (smokePuffs.get(i).getX()<-100)
                {
                    smokePuffs.remove(i);
                }
            }

        }
        else
        {
            player.resetDy();
            if (!reset)
            {
                newGameCreated=false;
                startReset = System.nanoTime();
                reset = true;
                dissappear = true;

                explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),
                        player.getX()+25,player.getY()-25,100,100,25);
            }

            explosion.update();
            long resetElasped = (System.nanoTime()-startReset)/1000000;

            if (!firstTime)
            {
                resetElasped = 2501;
                firstTime = true;
            }

            if (resetElasped>2500 && !newGameCreated)
            {
                newGame();
            }
        }
    }

    public boolean collision(GameObject a, GameObject b)
    {
        if (Rect.intersects(a.getRectangle(),b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX =(float) getWidth()/WIDTH;
        final float scaleFactorY = (float) getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if (!dissappear)
                player.draw(canvas);

            if (started)
                explosion.draw(canvas);

            for (SmokePuff sp: smokePuffs)
            {
                sp.draw(canvas);
            }

            for (Missile m: missiles)
            {
                m.draw(canvas);
            }

            for (BottomBorder bb:bottomBorders)
            {
                bb.draw(canvas);
            }

            for (TopBorder tb: topBorders)
            {
                tb.draw(canvas);
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void updateTopBorder()
    {
        if (player.getScore()%50==0)
        {
            topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                    topBorders.get(topBorders.size()-1).getX()+20,0,(int) (random.nextDouble()*maxBorderHeight)+1));
        }

        for (int i=0;i<topBorders.size();i++)
        {
            topBorders.get(i).update();

            if (topBorders.get(i).getX()<-20)
            {
                topBorders.remove(i);

                if (topBorders.get(topBorders.size() - 1).getHeight() >= maxBorderHeight) {
                    topDown = false;
                }
                if (topBorders.get(topBorders.size() - 1).getHeight() <= minBorderHeight) {
                    topDown = true;
                }
                if (topDown) {
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            topBorders.get(topBorders.size() - 1).getX() + 20, 0,
                            topBorders.get(topBorders.size() - 1).getHeight() + 1));
                } else {
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            topBorders.get(topBorders.size() - 1).getX() + 20, 0,
                            topBorders.get(topBorders.size() - 1).getHeight() - 1));
                }
            }
        }
    }

    public void updateBottomBorder()
    {
        if (player.getScore()%40==0)
        {
            bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                    bottomBorders.get(bottomBorders.size()-1).getX()+20,
                    (int) ((random.nextDouble()*maxBorderHeight)+(HEIGHT-maxBorderHeight))));
        }

        for (int i=0;i<bottomBorders.size();i++) {
            bottomBorders.get(i).update();
            if (bottomBorders.get(i).getX() < -20) {
                bottomBorders.remove(i);

                if (bottomBorders.get(bottomBorders.size() - 1).getY() <=HEIGHT - maxBorderHeight) {
                    bottomDown = true;
                }
                if (bottomBorders.get(bottomBorders.size() - 1).getY() >=HEIGHT - minBorderHeight) {
                    bottomDown = false;
                }
                if (bottomDown) {
                    bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            bottomBorders.get(bottomBorders.size() - 1).getX() + 20,
                            bottomBorders.get(bottomBorders.size() - 1).getY() + 1));
                } else {
                    bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            bottomBorders.get(bottomBorders.size() - 1).getX() + 20,
                            bottomBorders.get(bottomBorders.size() - 1).getY() - 1));
                }
            }
        }
    }

    public void newGame()
    {
        dissappear = false;

        smokePuffs.clear();
        missiles.clear();
        topBorders.clear();
        bottomBorders.clear();

        minBorderHeight = 5;
        maxBorderHeight = 30;

        player.resetDy();
        player.setY((int)(HEIGHT/2));

        if (player.getScore()>mBest)
        {
            mBest=player.getScore();
        }

        player.resetScore();

        for (int i=0; i*20<WIDTH+40;i++)
        {
            if (i==0)
            {
                topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                        i*20,0,10));
            }
            else
            {
                boolean temp=true;

                if (topBorders.get(i-1).getHeight()>maxBorderHeight)
                    temp = false;

                if (temp)
                {
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            i*20,0,topBorders.get(i-1).getHeight()+1));
                }
                else
                {
                    topBorders.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            i*20,0,topBorders.get(i-1).getHeight()-1));
                }
            }
        }

        for (int i=0;i*20< WIDTH+40;i++)
        {
            if (i==0)
            {
                bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                        i*20,(int)HEIGHT-minBorderHeight));
            }
            else
            {
                boolean temp=false;

                if (bottomBorders.get(i-1).getY()>=HEIGHT - minBorderHeight)
                    temp = false;

                if (bottomBorders.get(i-1).getY()<=HEIGHT-maxBorderHeight)
                    temp = true;

                if (temp)
                {
                    bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            i*20,bottomBorders.get(i-1).getY()+1));
                }
                else
                {
                    bottomBorders.add(new BottomBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            i*20,bottomBorders.get(i-1).getY()-1));
                }
            }
        }

        newGameCreated = true;

    }

    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("DISTANCE: "+ player.getScore(),10,HEIGHT-10,paint);
        canvas.drawText("BEST: "+ mBest,WIDTH-250,HEIGHT-10,paint);

        if(!player.getPlaying()&&newGameCreated&&reset)
        {
            Paint paint1 = new Paint();
            paint1.setTextSize(50);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2-50, HEIGHT/2, paint1);

            paint1.setTextSize(30);
            canvas.drawText("PRESS AND HOLD TO GO UP", WIDTH/2-50, HEIGHT/2 + 30, paint1);
            canvas.drawText("RELEASE TO GO DOWN", WIDTH/2-50, HEIGHT/2 + 60, paint1);
        }
    }


}
