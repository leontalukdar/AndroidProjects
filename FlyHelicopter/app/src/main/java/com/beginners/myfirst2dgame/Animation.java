package com.beginners.myfirst2dgame;

import android.graphics.Bitmap;

/**
 * Created by LEON on 5/16/2017.
 */

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setFrame(int i){
        currentFrame = i;
    }

    public void setDelay(long d){
        delay =d;
    }

    public void update(){
        long elasped = System.nanoTime()-startTime;
        if (elasped>delay){
            currentFrame++;
            startTime= System.nanoTime();
        }

        if (currentFrame==frames.length){
            currentFrame=0;
            playedOnce=true;
        }
    }

    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }

    public boolean isPlayedOnce(){
        return playedOnce;
    }


}
