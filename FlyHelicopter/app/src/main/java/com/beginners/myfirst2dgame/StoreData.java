package com.beginners.myfirst2dgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by LEON on 5/19/2017.
 */

public class StoreData extends Activity {

    @Override
    protected void onCreate(Bundle state){
        super.onCreate(state);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("SCORE", GamePanel.mBest);
        editor.commit();
    }

}
