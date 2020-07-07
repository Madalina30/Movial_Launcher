package com.movial.launcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    //definitions
    static List<ApplicationInfo> listOfApps;
    PackageManager pm;
    static IntentFilter filter;
    static MyReceiver receiver;
    Context context = this;
    RelativeLayout settings;
    Pagination pagination;
    int page;
    int mLeft, mRight, imgWidth, imgHeight;
    public GridLayout[] apps;
    LinearLayout linearLayout;
    public int numberOfColumns;
    public int appsPerPage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the screen will not be able to be in the landscape mode
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //for the navigation bar and notification bar to be transparent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //general setup
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        filter.addDataScheme("package");
        receiver = new MyReceiver();
        registerReceiver(receiver, filter); //+unregister
        settings = findViewById(R.id.settings);
        pm = getPackageManager();
        listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        //setting default values
        if (appsPerPage == 0) appsPerPage = 24;
        if(numberOfColumns == 0) numberOfColumns = 4;
        if (mLeft == 0 && mRight == 0) mLeft = mRight = 35;
        if (imgWidth == 0 && imgHeight == 0) imgWidth = imgHeight = 170;

        //Take the values from sharedPreferences
        numberOfColumns = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("numberOfColumns", 4);
        appsPerPage = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("appsPerPage", 24);
        mLeft = mRight = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("mLeft", 35);
        imgWidth = imgHeight = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("imgWidth", 170);

        pagination = new Pagination(appsPerPage, this);
        apps = pagination.buildList(listOfApps, numberOfColumns, mLeft, mRight, imgWidth, imgHeight);

        linearLayout = findViewById(R.id.designBase);

        //app list processing

        SwipeHandler swipe = new SwipeHandler(this, linearLayout, apps);
        linearLayout.addView(apps[0]);
        swipe.Swipe();

        //button for settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(context, "AICI SE OPRESTE", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
