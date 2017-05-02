package com.example.leon.myapplication;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void  myFirstMethod(View v){
        Button btn=(Button)findViewById(R.id.button);
        View temp = findViewById(R.id.textView);
        i%=2;
        if (i%2==0){
            temp.setVisibility(View.VISIBLE);
            btn.setText("OFF");
        }
        else {
            temp.setVisibility(View.INVISIBLE);
            btn.setText("ON");
        }
        i++;
    }

}
