/*
Sets the number of columns and the dimension of the icon
* */
package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity {
    //Definitions
    Context context;
    RadioButton column3, column4, column5;
    RadioButton small, medium, large;
    Button apply;
    Button backToDefault;

    public static int numberOfColumns;
    public static int appsPerPage;
    public static int mLeft;
    public static int mRight;
    public static int imgWidth;
    public static int imgHeight;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //the screen will not be able to be in the landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        //number of columns
        column3 = findViewById(R.id.columns3);
        column4 = findViewById(R.id.columns4);
        column5 = findViewById(R.id.columns5);

        //icon dimension
        small = findViewById(R.id.small);
        medium = findViewById(R.id.medium);
        large = findViewById(R.id.large);

        //apply button - apply settings and go back to apps
        apply = findViewById(R.id.apply);

        //back to default button - apply default settings
        backToDefault = findViewById(R.id.backToDefault);

        //buttons for the column settings
        column3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 50;
                numberOfColumns = 3;
                appsPerPage = 18;
                //saving data
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("mLeft", 50).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("numberOfColumns", 3).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("appsPerPage", 18).apply();
            }
        });
        column4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 35;
                numberOfColumns = 4;
                appsPerPage = 24;
                //saving data
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("mLeft", 35).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("numberOfColumns", 4).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("appsPerPage", 24).apply();
            }
        });
        column5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 20;
                numberOfColumns = 5;
                appsPerPage = 30;
                //saving data
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("mLeft", 20).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("numberOfColumns", 5).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("appsPerPage", 30).apply();
            }
        });

        //buttons for the icon size settings
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWidth = imgHeight = 150;
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("imgWidth", 150).apply();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWidth = imgHeight = 170;
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("imgWidth", 170).apply();
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfColumns == 5) {
                    imgWidth = imgHeight = 170;
                    getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("imgWidth", 170).apply();
                } else {
                    imgWidth = imgHeight = 190;
                    getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("imgWidth", 190).apply();
                }
            }
        });

        //button for applying the settings
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //button for default settings
        backToDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = 35;
                numberOfColumns = 4;
                appsPerPage = 24;
                imgWidth = 170;
                //saving data
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("mLeft", 35).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("numberOfColumns", 4).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("appsPerPage", 24).apply();
                getSharedPreferences("gridValues", MODE_PRIVATE).edit().putInt("imgWidth", 170).apply();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
