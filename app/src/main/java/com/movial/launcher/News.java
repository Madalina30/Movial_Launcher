/*
Under construction
* */
package com.movial.launcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;

public class News extends AppCompatActivity {
    //definitions
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView newsSection;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    SearchView searchGoogle;
    private TextView loading;
    LinearLayout linearLayout;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    JSONArray data;
    JSONObject obj;
    String NEWS_API;
    private String[] categories = {"business", "technology", "entertainment", "sport", "science", "health", "general"};

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

        // Instantiations
        requestQueue = Volley.newRequestQueue(this);
        loading = findViewById(R.id.loading);
        swipeRefreshLayout = findViewById(R.id.refresh);
        searchGoogle = findViewById(R.id.google);
        newsSection = findViewById(R.id.recyclerView);
        linearLayout = findViewById(R.id.linearLayout);

        searchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                startActivity(intent);
                finish();
            }
        });

        newsSection.setHasFixedSize(true);
        newsSection.setLayoutManager(new LinearLayoutManager(this));
        newsSection.setItemAnimator(new DefaultItemAnimator());

        listItems = new ArrayList<>();

        //choosing a random category for the news

        chooseRandomCategory();
        infosFromUrl();
        adapter = new MyAdapter(News.this, listItems, obj);
        newsSection.setAdapter(adapter);

        //refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading.setVisibility(View.GONE);
                newsSection.removeAllViewsInLayout();
                chooseRandomCategory();
                infosFromUrl();
                swipeRefreshLayout.setRefreshing(false);
                adapter = new MyAdapter(News.this, listItems, obj);
                newsSection.setAdapter(adapter);
            }
        });

        SwipeHandler swipeToApps = new SwipeHandler(this, swipeRefreshLayout);
        swipeToApps.swipeRight();
    }

    private void chooseRandomCategory() {
        int number = (int) Math.floor(Math.random() * 6);
        String category = categories[number];
        NEWS_API = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=5a5cf98cf6344a0795cd5d6cc61bfa31";
    }

    private void infosFromUrl() {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NEWS_API,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            data = response.getJSONArray("articles");
                            for (int i = 0; i < 20; i++) {
                                obj = (JSONObject) data.get(i);
                                ListItem item = new ListItem(obj.getString("title"), obj.getString("description"), obj.getString("urlToImage"));
                                listItems.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(News.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
