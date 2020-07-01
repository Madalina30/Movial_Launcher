package com.movial.launcher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AppListBuilder extends AppCompatActivity {
    //definitions
    LinearLayout apkButton;
    ImageView apkImage;
    TextView apkName;
    ApplicationInfo app;
    GridLayout apps;
    Context context;

    AppListBuilder(ApplicationInfo app, GridLayout apps,Context context){
        this.app = app;
        this.apps = apps;
        this.context = context;
    }

    public void buildApp(){
        apkButton = new LinearLayout(context);
        LinearLayout.LayoutParams apk = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        apk.setMargins(25, 15, 25, 15);
        apk.gravity = Gravity.CENTER;
        apkButton.setLayoutParams(apk);
        apkButton.setOrientation(LinearLayout.VERTICAL);

        apkImage = new ImageView(context);
        LinearLayout.LayoutParams img = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        img.width = 150;
        img.height = 150;
        apkImage.setLayoutParams(img);

        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(app.packageName, 0);
            apkImage.setBackground(context.getPackageManager().getApplicationIcon(appInfo));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        apkName = new TextView(context);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        text.gravity = Gravity.CENTER;
        apkName.setLayoutParams(text);
        apkName.setTextSize(12);

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
        apps.addView(apkButton);
        apkButton.addView(apkImage);
        apkButton.addView(apkName);

        //launch app
        apkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp();
            }
        });
    }

    public void openApp(){
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
