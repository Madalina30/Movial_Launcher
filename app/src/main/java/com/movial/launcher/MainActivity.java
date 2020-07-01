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
    //definitions
    GridLayout apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       //general setup
       apps = findViewById(R.id.buttons);
       final PackageManager pm = getPackageManager();
       List<ApplicationInfo> listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

       //app list processing
       for(ApplicationInfo app: listOfApps){
           //removing system apps
           Intent intent = getPackageManager().getLaunchIntentForPackage(app.packageName);
           if (intent != null) {
               /*Design creation*/
               AppListBuilder design = new AppListBuilder(app,apps,this);
               design.buildApp();
           }
       }
    }

}
