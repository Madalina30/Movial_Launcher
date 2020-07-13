/*
Creates the components
* */
package com.movial.launcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class DesignComponents extends AppCompatActivity {
    //definitions
    GridLayout grid;
    LinearLayout apkButton;
    ImageView apkImage;
    TextView apkName;

    public GridLayout createGridLayout(Context context, int width, int height, int nrColumns) {
        grid = new GridLayout(context);
        LinearLayout.LayoutParams apkGridLayoutParams = new LinearLayout.LayoutParams(width, height);
        grid.setLayoutParams(apkGridLayoutParams);
        grid.setBackground(Drawable.createFromPath("@android:color/transparent"));
        grid.setColumnCount(nrColumns);
        return grid;
    }

    public LinearLayout createLinearLayout(Context context, int width, int height, int mLeft, int mTop, int mRight, int mBottom) {
        //-2 = wrap, -1 = match
        //default margins 25, 15, 25, 15
        apkButton = new LinearLayout(context);
        LinearLayout.LayoutParams apkLinearLayoutParams = new LinearLayout.LayoutParams(width, height);
        apkLinearLayoutParams.setMargins(mLeft, mTop, mRight, mBottom);
        apkLinearLayoutParams.gravity = Gravity.CENTER;
        apkButton.setLayoutParams(apkLinearLayoutParams);
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
        apkImage.setAdjustViewBounds(true);
        return apkImage;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TextView createTextView(Context context, int width, int height, int textSize) {
        apkName = new TextView(context);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(width, height);
        text.gravity = Gravity.CENTER;
        apkName.setLayoutParams(text);
        apkName.setTextSize(textSize); //12
        apkName.setTextColor(Color.WHITE);
        apkName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        apkName.setBackground(Drawable.createFromPath("@android:color/transparent"));
        return apkName;
    }
}
