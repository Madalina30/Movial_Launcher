package com.movial.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    PackageManager pm;
    List<ApplicationInfo> listOfApps;
    MainActivity apps;
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();

        //to see what action the receiver does
        sb.append("Action: " + intent.getAction() + "\n");

        //to see properties of the package
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        System.out.println(log);
        if (intent.getAction() == Intent.ACTION_PACKAGE_REMOVED){
            //when an app is uninstalled
            Toast.makeText(context, "APLICATIE DEEEEZINSTALATA", Toast.LENGTH_SHORT).show();

        } else if (intent.getAction() == Intent.ACTION_PACKAGE_ADDED){
            //when an app is installed
            Toast.makeText(context, "APLICATIE INSTALATA", Toast.LENGTH_SHORT).show();
        }
    }
}
