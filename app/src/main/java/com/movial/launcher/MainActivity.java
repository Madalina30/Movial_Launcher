package com.movial.launcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.List;


public class MainActivity extends AppCompatActivity {
    //definitions
    private IntentFilter filter;
    private MyReceiver receiver;
    private int numberOfColumns;
    private int appsPerPage;
    private GridLayout[] apps;
    private int mLeft, mRight, imgWidth, imgHeight;
    private Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the screen will not be able to be in the landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //for the navigation bar and notification bar to be transparent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //setup for receiver
        setUpReceiver();

        //Take the values from sharedPreferences
        takeFromSharedPreferences();

        //getting the apps + pagination
        designingTheListOfApps();

        //for the swipe between apps and news gestures
        forSwipe();

        //button for settings
        RelativeLayout settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
            }
        });
    }

    private void takeFromSharedPreferences() {
        numberOfColumns = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("numberOfColumns", 4);
        appsPerPage = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("appsPerPage", 24);
        mLeft = mRight = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("mLeft", 35);
        imgWidth = imgHeight = getSharedPreferences("gridValues", MODE_PRIVATE).getInt("imgWidth", 170);
    }

    private void setUpReceiver() {
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        filter.addDataScheme("package");
        receiver = new MyReceiver(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void designingTheListOfApps() {
        List<ApplicationInfo> listOfApps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        Pagination pagination = new Pagination(appsPerPage, this);
        apps = pagination.buildList(listOfApps, numberOfColumns, mLeft, mRight, imgWidth, imgHeight);
    }

    private void forSwipe() {
        LinearLayout linearLayout = findViewById(R.id.designBase);
        SwipeHandler swipeLeftRight = new SwipeHandler(this, linearLayout, apps);
        linearLayout.addView(apps[0]);
        swipeLeftRight.Swipe();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
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
