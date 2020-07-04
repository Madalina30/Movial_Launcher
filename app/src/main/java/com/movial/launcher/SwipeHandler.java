/*
For left and right swipe
* */
package com.movial.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SwipeHandler {
    Activity activity;
    ScrollView scroll;
    public int pageNumber = 0;
    public float x1, x2;
    LinearLayout workOn;
    GridLayout[] apps;

    SwipeHandler(Activity activity, LinearLayout workOn, GridLayout[] apps) {
        this.activity = activity;
        this.workOn = workOn;
        this.apps = apps;
    }

    SwipeHandler(Activity activity, ScrollView scroll) {
        this.activity = activity;
        this.scroll = scroll;
    }

    public void Swipe() {
        GridLayout grid = ((GridLayout) workOn.getChildAt(pageNumber + 1));
        for (int i = 0; i < grid.getChildCount(); i++) {
            grid.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x1 = event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            x2 = event.getX();
                            checkDirection(x1, x2);
                            break;
                    }
                    return true;
                }
            });
        }
        workOn.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                GridLayout grid = ((GridLayout) workOn.getChildAt(2));
                for (int i = 0; i < grid.getChildCount(); i++) {
                    grid.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    x1 = event.getX();
                                    break;
                                case MotionEvent.ACTION_UP:
                                    x2 = event.getX();
                                    checkDirection(x1, x2);
                                    break;
                            }
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
        workOn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        checkDirection(x1, x2);
                        break;
                }
                return true;
            }
        });
    }

    private void checkDirection(float x1, float x2) {
        if (x1 > x2) {
            if (pageNumber != -1) {
                pageNumber++;
                if (apps[pageNumber] == null) {
                    pageNumber = pageNumber - 1;
                } else {
                    workOn.addView(apps[pageNumber]);
                    workOn.removeView(apps[pageNumber - 1]);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(200)
                            .playOn(workOn);
                }
            }
        } else {
            pageNumber--;
            if (pageNumber == -1) {
                //it goes to the NewsAndWeather class when swipe left
                Intent intent = new Intent(activity, News.class);
                activity.startActivity(intent);
                pageNumber = 0;
            } else {
                workOn.addView(apps[pageNumber]);
                workOn.removeView(apps[pageNumber + 1]);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(200)
                        .playOn(workOn);
            }
        }
    }
    public void swipeRight(){
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
