/*
List of apps
* */
package com.movial.launcher;

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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class AppListBuilder extends AppCompatActivity {
    //definitions
    LinearLayout apkButton;
    ImageView apkImage;
    TextView apkName;
    ApplicationInfo app;
    Context context;

    AppListBuilder(ApplicationInfo app, Context context) {
        this.app = app;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LinearLayout buildApp(int mLeft, int mRight, int imgWidth, int imgHeight) {
        DesignComponents design = new DesignComponents();
        //button
        apkButton = design.createLinearLayout(context, -2, -2, mLeft, 35, mRight, 35);

        //apkImage
        apkImage = design.createImageView(context, -2, -2, imgWidth, imgHeight);
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(app.packageName, 0);
            apkImage.setBackground(context.getPackageManager().getApplicationIcon(appInfo));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //apkName
        apkName = design.createTextView(context, -2, -2, 12);
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(app.packageName, 0);
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

    public void openApp() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.packageName);
        if (intent != null) {
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "App not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
