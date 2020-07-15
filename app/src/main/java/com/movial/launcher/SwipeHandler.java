/*
For left and right swipe
* */
package com.movial.launcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

class SwipeHandler {
    //definitions
    private Activity activity;
    private ScrollView scroll;
    private GridView gridView;
    private LinearLayout linearLayout;
    private float x1, x2;

    SwipeHandler(Activity activity, LinearLayout linearLayout, GridView gridView) {
        this.activity = activity;
        this.linearLayout = linearLayout;
        this.gridView = gridView;
    }

    SwipeHandler(Activity activity, ScrollView scroll) {
        this.activity = activity;
        this.scroll = scroll;
    }

    @SuppressLint("ClickableViewAccessibility")
    void Swipe() {
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        float valueX = x2 - x1;
                        if(Math.abs(valueX) > 150){
                            if(x1 < x2){
                                Intent intent = new Intent(activity, News.class);
                                activity.startActivity(intent);
                            }
                            else {
                                Toast.makeText(activity, "Swipe left", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
                return false;
            }
        });
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        float valueX = x2 - x1;
                        if(Math.abs(valueX) > 150){
                            if(x1 < x2){
                                Intent intent = new Intent(activity, News.class);
                                activity.startActivity(intent);
                            }
                            else {
                                Toast.makeText(activity, "Swipe left", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
                return false;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    void swipeRight() {
        //from the news section to the apps
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float valueX = x1 - x2;
                        if(Math.abs(valueX) > 150) {
                            if (x1>x2) {
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }
}
