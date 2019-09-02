package com.mona.moviesapp.popular_people;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        String popularurl = "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421";
        new JsonData().execute(popularurl);
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

            ArrayList<PopularInfo> popularInfos = new ArrayList<>();
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

                MyListAdapter adapter = new MyListAdapter(popularInfos, PopularListActivity.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(PopularListActivity.this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
