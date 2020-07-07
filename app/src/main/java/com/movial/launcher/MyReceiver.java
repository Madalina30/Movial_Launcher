package com.movial.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    PackageManager pm;
    List<ApplicationInfo> listOfApps;
    MainActivity apps;
    @Override
    public void onReceive(Context context, Intent intent) {
        pm = context.getPackageManager();
        listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        apps = new MainActivity();
        if (listOfApps != apps.listOfApps && listOfApps != null && apps.listOfApps != null) {
            //here the app that doesn't belong there will be removed and the one installed will be shown
            if (listOfApps.size() > apps.listOfApps.size()) {
                //if an app is installed - nu merge la acelasi pachet instalat
                Toast.makeText(context, "APLICATIE INSTALATA", Toast.LENGTH_SHORT).show();
            } else if (listOfApps.size() < apps.listOfApps.size()) {
                //if an app is uninstalled
                Toast.makeText(context, "APLICATIE DEEEEZINSTALATA", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "ACELASI PACHET", Toast.LENGTH_SHORT).show();
        }
    }
}
