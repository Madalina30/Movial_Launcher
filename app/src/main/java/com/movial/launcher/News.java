/*
Under construction
* */
package com.movial.launcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class News extends AppCompatActivity {
    //definitions
    SwipeRefreshLayout swipeRefreshLayout;
    Activity activity;
    Context context;
    ScrollView scroll;
    TextView title;
    ImageView img;
    LinearLayout newsSection, newNews;
    SearchView searchGoogle;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    String[] categories = {"business", "technology", "entertainment", "sport", "science", "health", "general"};
    String category;
    String NEWS_API;
    float x1, x2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //the screen will not be able to be in the landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //for the navigation bar and notification bar to be transparent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //adding context and activity
        context = this;
        activity = this;

        int number = (int) Math.floor(Math.random() * 6);
        category = categories[number];

        NEWS_API = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=5a5cf98cf6344a0795cd5d6cc61bfa31";
        newsSection = findViewById(R.id.news);

        //refresh
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

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
        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NEWS_API, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //construction of elements
                        title = designComponents.createTextView(context, -2, -2, 12);
                        img = designComponents.createImageView(context, -2, -2, 300, 300);
                        newNews = designComponents.createLinearLayout(context, -1, -2, 40, 40, 40, 40);
                        newNews.setOrientation(LinearLayout.HORIZONTAL);
                        newNews.setBackgroundColor(Color.parseColor("#3a3a3a"));

                        //getting information from the internet
                        JSONArray data = response.getJSONArray("articles");
                        JSONObject obj = (JSONObject) data.get(finalI);

                        int size = (int) Math.floor((Math.random() * 350) + 300);

                        //setting image
                        img.setBackgroundResource(R.drawable.ic_bunny);
                        img.getLayoutParams().width = 400;
                        img.getLayoutParams().height = 250;
                        Picasso.get().load(obj.getString("urlToImage")).resize(400, 250).into(img);
                        newNews.addView(img);

                        //setting news title
                        title.setText(obj.getString("title"));
                        title.setPadding(20, 0, 0, 0);
                        newNews.addView(title);
                        newsSection.addView(newNews);

                        //going to the news site
                        final JSONObject objF = obj;
                        newNews.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = objF.getString("url");
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        title.setText("" + e);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);

        }
    }

}
