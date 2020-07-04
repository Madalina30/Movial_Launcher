/*
Under construction
* */
package com.movial.launcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class News extends AppCompatActivity {
    //definitions
    Activity activity;
    Context context;
    ScrollView scroll;
    TextView title;
    ImageView img;
    LinearLayout newsSection, newNews;
    SearchView searchGoogle;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    String[] categories = {"tech","it","food","history","sport"};
    String category;
    String NEWS_API;
    float x1, x2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setting transparent notification bar and navigation bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //adding context and activity
        context = this;
        activity = this;
        int plm = (int) Math.floor(Math.random()*4);
        category = categories[plm];
        NEWS_API = "https://www.reddit.com/search.json?q="+category+"&limit=10";
        Toast.makeText(this, category+" "+NEWS_API,Toast.LENGTH_SHORT).show();
        newsSection = findViewById(R.id.news);

        //Instantiate the search bar
        searchGoogle = findViewById(R.id.google);
        searchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                startActivity(intent);
                finish();
            }
        });

        //Instantiate scroll
        scroll = findViewById(R.id.scroll);
        SwipeHandler swipe = new SwipeHandler(activity, scroll);
        swipe.swipeRight();

        // Instantiate the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        final DesignComponents designComponents = new DesignComponents();
        // Request a string response from the provided URL
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NEWS_API, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        title = designComponents.createTextView(context, -2, -2, 12);
                        img = designComponents.createImageView(context, -2, -2, 300, 300);
                        newNews = designComponents.createLinearLayout(context, -1, -2, 40, 40, 40, 40);
                        newNews.setOrientation(LinearLayout.HORIZONTAL);
                        newNews.setBackgroundColor(Color.parseColor("#3a3a3a"));
                        JSONObject data = response.getJSONObject("data");
                        JSONObject obj = (JSONObject) data.getJSONArray("children").get(finalI);
                        int size = (int) Math.floor((Math.random() * 350 ) + 300);
                        Picasso.get().load("https://picsum.photos/" + size + "/300").resize(300, 300).into(img);
                        newNews.addView(img);
                        title.setText(obj.getJSONObject("data").getString("title"));
                        title.setPadding(20, 0, 0, 0);
                        newNews.addView(title);
                        newsSection.addView(newNews);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        title.setText("" + e);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    title.setText("nu merge");
                }
            });
            requestQueue.add(jsonObjectRequest);

        }
    }

}
