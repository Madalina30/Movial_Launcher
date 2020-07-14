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
import android.widget.ScrollView;

class SwipeHandler {
    //definitions
    private Activity activity;
    private ScrollView scroll;
    private GridView gridView;
    private float x1, x2;

    SwipeHandler(Activity activity, GridView gridView) {
        this.activity = activity;
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
                        if (x1 < x2){
                            Intent intent = new Intent(activity, News.class);
                            activity.startActivity(intent);
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
                        if ((x1 - 200) > x2) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                        break;
                }
                return false;
            }
        });
    }
}
