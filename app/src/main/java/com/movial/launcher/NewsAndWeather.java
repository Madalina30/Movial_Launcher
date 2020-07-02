/*
Under construction
* */
package com.movial.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;

public class NewsAndWeather extends AppCompatActivity {
    TextView title;
    ImageView img;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    String NEWS_API = "https://www.reddit.com/search.json?q=tech&limit=10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_weather);
        // Instantiate the RequestQueue
        title = findViewById(R.id.title);
        img = findViewById(R.id.img);
        requestQueue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NEWS_API, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject data = response.getJSONObject("data");
                    JSONObject obj = (JSONObject) data.getJSONArray("children").get(0);
                    title.setText(obj.getJSONObject("data").getString("title").toString());
                    JSONObject imgz = (JSONObject) obj.getJSONObject("data").getJSONObject("preview").getJSONArray("images").get(0);
                    img.setImageDrawable(LoadImageFromWebOperations(imgz.getJSONObject("source").getString("url").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    title.setText(""+e);
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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "s"+is);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
