package com.beginners.collectcandy;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by LEON on 5/21/2017.
 */

public class SoundPlayer {

    private SoundPool soundPool;
    private int hitSound;
    private int overSound;

    public SoundPlayer(Context context)
    {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        hitSound = soundPool.load(context,R.raw.hit,1);
        overSound = soundPool.load(context,R.raw.over,1);
    }

    public void playHitSound()
    {
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playOverSound()
    {
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }
}
