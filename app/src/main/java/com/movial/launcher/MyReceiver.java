package com.movial.launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {
    //definitions
    private Activity activity;
    private LinearLayout linearLayout;
    private List<AppInfo> apps;
    private ArrayAdapter<AppInfo> adapter;

    MyReceiver(Activity activity, LinearLayout linearLayout, List<AppInfo> apps, ArrayAdapter<AppInfo> adapter) {
        this.activity = activity;
        this.linearLayout = linearLayout;
        this.apps = apps;
        this.adapter = adapter;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getDataString();

        linearLayout.invalidate();
        linearLayout.removeAllViewsInLayout();
        linearLayout.requestLayout();

        if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            //when an app is uninstalled
            Toast.makeText(context, "APP UNINSTALLED", Toast.LENGTH_SHORT).show();
            Log.e("MyReceiver", "UninstallReceiver:packageName: " + packageName);

        } else if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            //when an app is installed
            Toast.makeText(context, "APP INSTALLED", Toast.LENGTH_SHORT).show();
            Log.e("MyReceiver", "InstallReceiver:packageName: " + packageName);

        }
    }
}
