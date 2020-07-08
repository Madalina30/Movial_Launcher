package com.movial.launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {
    //definitions
    Activity activity;

    MyReceiver(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            //when an app is uninstalled
            Toast.makeText(context, "APP UNINSTALLED", Toast.LENGTH_SHORT).show();
            activity.finish();
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            //when an app is installed
            Toast.makeText(context, "APP INSTALLED", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }
}
