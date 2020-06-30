package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

public class MainActivity extends AppCompatActivity {
    GridLayout apps;
    LinearLayout apkButton;
    ImageView apkImage;
    TextView apkName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       apps = findViewById(R.id.buttons);
       final PackageManager pm = getPackageManager();
       List<ApplicationInfo> listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
       for(ApplicationInfo app: listOfApps){
           Intent intent = getPackageManager().getLaunchIntentForPackage(app.packageName);
           if (intent != null) {
               apkButton = new LinearLayout(this);
               LinearLayout.LayoutParams apk = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               apk.setMargins(25, 15, 25, 15);
               apk.gravity = Gravity.CENTER;
               apkButton.setLayoutParams(apk);
               apkButton.setOrientation(LinearLayout.VERTICAL);

               apkImage = new ImageView(this);
               LinearLayout.LayoutParams img = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               img.width = 150;
               img.height = 150;
               apkImage.setLayoutParams(img);
               try {
                   ApplicationInfo appInfo = getPackageManager().getApplicationInfo(app.packageName, 0);
                   apkImage.setBackground(getPackageManager().getApplicationIcon(appInfo));
               } catch (PackageManager.NameNotFoundException e) {
                   e.printStackTrace();
               }

               apkName = new TextView(this);
               LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               text.gravity = Gravity.CENTER;
               apkName.setLayoutParams(text);
               try {
                   ApplicationInfo appInfo = getPackageManager().getApplicationInfo(app.packageName, 0);
                   String name = getPackageManager().getApplicationLabel(appInfo).toString();
                   if (name.length() > 7)
                       name = name.substring(0, 7) + "...";
                   apkName.setText(name);
               } catch (PackageManager.NameNotFoundException e) {
                   e.printStackTrace();
               }
               apkName.setTextSize(12);

               //packageName pt deschis app

               apps.addView(apkButton);
               apkButton.addView(apkImage);
               apkButton.addView(apkName);
               final ApplicationInfo fapp = app;
               apkButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = getPackageManager().getLaunchIntentForPackage(fapp.packageName);
                       if (intent != null) {
                           try {
                               startActivity(intent);
                           } catch (ActivityNotFoundException e) {
                               e.printStackTrace();
                               Toast.makeText(getApplicationContext(), "App not found", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
               });
           }
       }
    }

}
