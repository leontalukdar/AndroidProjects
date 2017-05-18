package com.beginners.myfirst2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by LEON on 5/18/2017.
 */

public class SmokePuff extends GameObject {

    private int r=10;

    public SmokePuff(int x,int y){
        super.x=x;
        super.y=y;
    }

    public void update()
    {
        x-=25;
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x-r,y-r,r,paint);
        canvas.drawCircle(x-r+2,y-r+2,r,paint);
        canvas.drawCircle(x-r+4,y-r+4,r,paint);
    }

}
