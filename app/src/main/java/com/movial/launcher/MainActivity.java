package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

public class MainActivity extends AppCompatActivity {
    //definitions
    int page;
    public GridLayout[] apps;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //general setup

        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> listOfApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        Pagination pagination = new Pagination(24, this);

        apps = pagination.buildList(listOfApps);

        linearLayout = findViewById(R.id.designBase);

        //app list processing

        SwipeHandler swipe = new SwipeHandler(this, linearLayout, apps);
        linearLayout.addView(apps[0]);
        swipe.Swipe();

        System.out.println(listOfApps.size());
    }
}
