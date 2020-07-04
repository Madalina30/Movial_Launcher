package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    Context context;
    RadioButton column3, column4, column5;
    RadioButton apps16, apps20, apps24;
    RadioButton small, medium, large;
    Button apply;
    public static int numberOfColumns;
    public static int appsPerPage;
    public static int mLeft;
    public static int mRight;
    public static int imgWidth;
    public static int imgHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this;
        //number of columns
        column3 = findViewById(R.id.columns3);
        column4 = findViewById(R.id.columns4);
        column5 = findViewById(R.id.columns5);

        //icon dimension
        small = findViewById(R.id.small);
        medium = findViewById(R.id.medium);
        large = findViewById(R.id.large);

        //final button
        apply = findViewById(R.id.apply);

        column3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 50;
                numberOfColumns = 3;
                appsPerPage = 18;
            }
        });
        column4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 35;
                numberOfColumns = 4;
                appsPerPage = 24;
            }
        });
        column5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeft = mRight = 20;
                numberOfColumns = 5;
                appsPerPage = 30;
            }
        });

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWidth = imgHeight = 150;
                if (numberOfColumns == 5) appsPerPage = 35;
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWidth = imgHeight = 170;
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWidth = imgHeight = 190;
                if (numberOfColumns == 5){
                    imgWidth = imgHeight = 170;
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(context, MainActivity.class);
              startActivity(intent);
            }
        });
    }
}
