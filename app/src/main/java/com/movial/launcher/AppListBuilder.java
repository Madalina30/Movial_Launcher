/*
List of apps
* */
package com.movial.launcher;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


@SuppressLint("Registered")
class AppListBuilder{
    //definitions
    private ApplicationInfo app;
    private Context context;

    AppListBuilder(ApplicationInfo app, Context context) {
        this.app = app;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    LinearLayout buildApp(int mLeft, int mRight, int imgWidth, int imgHeight) {
        DesignComponents design = new DesignComponents();

        //button
        LinearLayout apkButton = design.createLinearLayout(context, -2, -2, mLeft, 35, mRight, 35);

        //apkImage - the icon of the app
        ImageView apkImage = design.createImageView(context, -2, -2, imgWidth, imgHeight);
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(app.packageName, 0);
            apkImage.setBackground(context.getPackageManager().getApplicationIcon(appInfo));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //apkName - the name of the app
        TextView apkName = design.createTextView(context, -2, -2, 12);
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(app.packageName, 0);
            //definitions
            String name = context.getPackageManager().getApplicationLabel(appInfo).toString();
            if (name.length() > 7)
                name = name.substring(0, 7) + "...";
            apkName.setText(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //design setup
        apkButton.addView(apkImage);
        apkButton.addView(apkName);

        //launch app
        apkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp();
            }
        });
        return apkButton;
    }

    private void openApp() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.packageName);
        if (intent != null) {
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
