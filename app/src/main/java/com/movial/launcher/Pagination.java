/*
Pagination
* */
package com.movial.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.widget.GridLayout;

import java.util.List;

public class Pagination {
    public GridLayout[] pages = new GridLayout[50];
    public int appsPerPage;
    public int pageNumber = 0;
    Context context;

    public Pagination(int appsPerPage, Context context) {
        this.appsPerPage = appsPerPage;
        this.context = context;
    }

    public GridLayout[] buildList(List<ApplicationInfo> listOfApps, int nrColumns, int mLeft, int mRight, int imgWidth, int imgHeight) {
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
