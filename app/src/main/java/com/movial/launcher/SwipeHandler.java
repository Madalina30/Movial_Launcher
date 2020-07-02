/*
For left and right swipe
* */
package com.movial.launcher;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SwipeHandler {
    Context context;
    public int pageNumber = 0;
    public float x1, x2;
    LinearLayout workOn;
    GridLayout[] apps;
    SwipeHandler(Context context, LinearLayout workOn, GridLayout[] apps){
        this.context = context;
        this.workOn = workOn;
        this.apps = apps;
    }
    public void Swipe() {
        workOn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 =  touchEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 =  touchEvent.getX();
                        checkDirection(x1, x2);
                        break;
                }
                return true;
            }
        });
    }
    private void checkDirection(float x1, float x2){
        if (x1 > x2) {
            pageNumber++;
            if (apps[pageNumber] == null){
                pageNumber = pageNumber - 1;
            } else {
                workOn.addView(apps[pageNumber]);
                workOn.removeView(apps[pageNumber - 1]);
                YoYo.with(Techniques.SlideInRight)
                        .duration(300)
                        .playOn(workOn);
            }
        } else {
            pageNumber--;
            if (pageNumber == -1){
                pageNumber = pageNumber + 1;
            } else {
                workOn.addView(apps[pageNumber]);
                workOn.removeView(apps[pageNumber + 1]);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(300)
                        .playOn(workOn);
            }
        }

    }
}
