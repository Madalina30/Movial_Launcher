/*
Under construction
* */
package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

public class News extends AppCompatActivity {
    TextView title;
    ImageView img;
    SearchView searchGoogle;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    String NEWS_API = "https://www.reddit.com/search.json?q=tech&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Instantiate the search bar
        searchGoogle = findViewById(R.id.google);
        searchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                startActivity(intent);
            }
        });

        // Instantiate the RequestQueue
        title = findViewById(R.id.title);
        img = findViewById(R.id.img);
        requestQueue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NEWS_API, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONObject obj = (JSONObject) data.getJSONArray("children").get(0);
                    title.setText(obj.getJSONObject("data").getString("title").toString());


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
