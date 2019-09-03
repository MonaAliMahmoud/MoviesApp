package com.mona.moviesapp.popular_people;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PopularListActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    MyListAdapter adapter;
    Handler handler;

    ArrayList<PopularInfo> popularInfos = new ArrayList<>();
    String popularurl;

    private int pagenum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);

        popularurl= "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;

        adapter = new MyListAdapter(popularInfos, PopularListActivity.this);
        recyclerView.setAdapter(adapter);

        new JsonData().execute(popularurl);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = layoutManager.getChildCount();
                int scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition();
                int totalItems = layoutManager.getItemCount();
                if(currentItems + scrolledItems == totalItems) {
                    pagenum++;
                    String newUrl = "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;
                    new JsonData().execute(newUrl);
                }
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        refresh();
                    }
                }, 1000);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class JsonData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                    return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject popularList = new JSONObject(result);
                JSONArray popular = popularList.getJSONArray("results");
                for (int i=0; i< popular.length(); i++) {
                    JSONObject popularResult = popular.getJSONObject(i);
                    PopularInfo popularInfo = new PopularInfo();
                    popularInfo.setName(popularResult.getString("name"));
                    popularInfo.setKnown_for_department(popularResult.getString("known_for_department"));
                    popularInfo.setProfile_path(popularResult.getString("profile_path"));
                    popularInfo.setId(popularResult.getInt("id"));
                    popularInfos.add(popularInfo);
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(){
        popularInfos.clear();
        adapter.notifyDataSetChanged();
        new JsonData().execute(popularurl);
    }
}
