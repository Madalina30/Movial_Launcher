package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

public class MainActivity extends AppCompatActivity {
    //definitions
    Context context = this;
    RelativeLayout settings;
    int page;
    int mLeft, mRight, imgWidth, imgHeight;
    public GridLayout[] apps;
    LinearLayout linearLayout;
    public int numberOfColumns;
    public int appsPerPage;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //general setup
        settings = findViewById(R.id.settings);
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

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

        Pagination pagination = new Pagination(appsPerPage, this);

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
