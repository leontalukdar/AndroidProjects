package com.beginners.myfirst2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by LEON on 5/18/2017.
 */

public class Explosion {
    private int x;
    private int y;
    private int height;
    private int width;
    private Animation animation = new Animation();
    private Bitmap spriteSheet;
    private int row;

    public Explosion(Bitmap res,int x,int y,int h,int w,int numFrames)
    {
        this.x=x;
        this.y=y;
        this.height=h;
        this.width=w;

        Bitmap[] image = new Bitmap[numFrames];
        spriteSheet = res;

        for (int i=0;i<image.length;i++)
        {
            if (i%5==0 && i>0)
                row++;
            image[i] = Bitmap.createBitmap(spriteSheet,(i-(5*row))*width,row*height,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(15);
    }

    public void update()
    {
        if (!animation.isPlayedOnce())
        {
            animation.update();
        }
    }

    public void draw(Canvas canvas)
    {
        if (!animation.isPlayedOnce())
        {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }
    }

    public int getHeight(){return height;}

}
