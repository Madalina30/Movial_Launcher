/*
Pagination
* */
package com.movial.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.widget.GridLayout;

import androidx.annotation.RequiresApi;

import java.util.List;

class Pagination {
    //definitions
    private GridLayout[] pages = new GridLayout[50];
    private int appsPerPage;
    private int pageNumber = 0;
    private Context context;

    Pagination(int appsPerPage, Context context) {
        this.appsPerPage = appsPerPage;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    GridLayout[] buildList(List<ApplicationInfo> listOfApps, int nrColumns, int mLeft, int mRight, int imgWidth, int imgHeight) {
        DesignComponents design = new DesignComponents();
        pages[pageNumber] = design.createGridLayout(context, -2, -2, nrColumns);
        int countGoodApps = 0;
        for (ApplicationInfo app : listOfApps) {
            //removing system apps
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.packageName);
            if (intent != null) {
                /*Design creation*/
                countGoodApps++;
                AppListBuilder build = new AppListBuilder(app, context);
                pages[pageNumber].addView(build.buildApp(mLeft, mRight, imgWidth, imgHeight));
                if (countGoodApps == appsPerPage) {
                    countGoodApps = 0;
                    pageNumber++;
                    pages[pageNumber] = design.createGridLayout(context, -2, -2, nrColumns);
                }
            }
        }
        return pages;
    }
}
