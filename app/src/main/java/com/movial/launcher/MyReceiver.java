package com.movial.launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {
    //definitions
    Activity activity;

    MyReceiver(Activity activity) {
        this.activity = activity;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getDataString();
        if (MainActivity.linearLayout != null) {
            MainActivity.linearLayout.removeAllViews();
            MainActivity.linearLayout.addView(MainActivity.settings);
        }
        MainActivity.listOfApps = null;
        MainActivity.listOfApps = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        MainActivity.apps = null;
        MainActivity.apps = MainActivity.pagination.buildList(MainActivity.listOfApps, MainActivity.numberOfColumns, MainActivity.mLeft, MainActivity.mRight, MainActivity.imgWidth, MainActivity.imgHeight);
        MainActivity.swipe = new SwipeHandler(activity, MainActivity.linearLayout, MainActivity.apps);
        MainActivity.linearLayout.addView(MainActivity.apps[0]);
        MainActivity.swipe.Swipe();

        if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            //when an app is uninstalled
            Toast.makeText(context, "APP UNINSTALLED", Toast.LENGTH_SHORT).show();
            Log.d("MyReceiver", "UninstallReceiver:packageName: " + packageName);

        } else if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            //when an app is installed
            Toast.makeText(context, "APP INSTALLED", Toast.LENGTH_SHORT).show();
            Log.d("MyReceiver", "InstallReceiver:packageName: " + packageName);

        } else if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            //when an app is updated
            Toast.makeText(context, "APP UPDATED", Toast.LENGTH_SHORT).show();
            Log.d("MyReceiver", "UpdatedReceiver:packageName: " + packageName);

        }
    }
}
