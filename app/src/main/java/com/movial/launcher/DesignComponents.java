/*
Creates the components
* */
package com.movial.launcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DesignComponents extends AppCompatActivity {

    GridLayout grid;
    LinearLayout apkButton;
    ImageView apkImage;
    TextView apkName;

    public GridLayout createGridLayout(Context context, int width, int height, int nrColumns) {
        grid = new GridLayout(context);
        LinearLayout.LayoutParams apk = new LinearLayout.LayoutParams(width, height);
        grid.setLayoutParams(apk);
        grid.setBackground(Drawable.createFromPath("@android:color/transparent"));
        grid.setColumnCount(nrColumns);
        return grid;
    }

    public LinearLayout createLinearLayout(Context context, int width, int height, int mLeft, int mTop, int mRight, int mBottom) {
        //-2 = wrap, -1 = match
        //default margins 25, 15, 25, 15
        apkButton = new LinearLayout(context);
        LinearLayout.LayoutParams apk = new LinearLayout.LayoutParams(width, height);
        apk.setMargins(mLeft, mTop, mRight, mBottom);
        apk.gravity = Gravity.CENTER;
        apkButton.setLayoutParams(apk);
        apkButton.setBackground(Drawable.createFromPath("@android:color/transparent"));
        apkButton.setOrientation(LinearLayout.VERTICAL);
        return apkButton;
    }

    public ImageView createImageView(Context context, int width, int height, int imgWidth, int imgHeight) {
        apkImage = new ImageView(context);
        LinearLayout.LayoutParams img = new LinearLayout.LayoutParams(width, height);
        img.width = imgWidth;
        img.height = imgHeight;
        apkImage.setLayoutParams(img);
        apkImage.setBackground(Drawable.createFromPath("@android:color/transparent"));
        return apkImage;
    }

    public TextView createTextView(Context context, int width, int height, int textSize) {
        apkName = new TextView(context);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(width, height);
        text.gravity = Gravity.CENTER;
        apkName.setLayoutParams(text);
        apkName.setTextSize(textSize); //12
        apkName.setTextColor(Color.WHITE);
        apkName.setBackground(Drawable.createFromPath("@android:color/transparent"));
        return apkName;
    }
}
