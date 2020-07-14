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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

@SuppressLint("Registered")
class DesignComponents {

    LinearLayout createLinearLayout(Context context, int width, int height, int mLeft, int mTop, int mRight, int mBottom) {
        //-2 = wrap, -1 = match
        //default margins 25, 15, 25, 15
        LinearLayout apkButton = new LinearLayout(context);
        LinearLayout.LayoutParams apkLinearLayoutParams = new LinearLayout.LayoutParams(width, height);
        apkLinearLayoutParams.setMargins(mLeft, mTop, mRight, mBottom);
        apkLinearLayoutParams.gravity = Gravity.CENTER;
        apkButton.setLayoutParams(apkLinearLayoutParams);
        apkButton.setBackground(Drawable.createFromPath("@android:color/transparent"));
        apkButton.setOrientation(LinearLayout.VERTICAL);
        return apkButton;
    }

    ImageView createImageView(Context context, int width, int height, int imgWidth, int imgHeight) {
        ImageView apkImage = new ImageView(context);
        LinearLayout.LayoutParams img = new LinearLayout.LayoutParams(width, height);
        img.width = imgWidth;
        img.height = imgHeight;
        apkImage.setLayoutParams(img);
        apkImage.setBackground(Drawable.createFromPath("@android:color/transparent"));
        apkImage.setAdjustViewBounds(true);
        return apkImage;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    TextView createTextView(Context context, int width, int height, int textSize) {
        TextView apkName = new TextView(context);
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
