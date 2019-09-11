package com.mona.moviesapp.popular_people.PopularListScreen.model;

import android.os.AsyncTask;
import android.view.Display;

import com.mona.moviesapp.popular_people.PopularListScreen.controller.Controller;
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

public class DataNetwork extends AsyncTask<String, String, String>{

    URL url = null;
    ArrayList<PopularInfo> popularInfos = new ArrayList<>();
    Model model;
    private Controller controller;

    public DataNetwork(Model model,  Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    @Override
    protected String doInBackground(String... urls)
    {

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
            for (int i = 0; i < popular.length(); i++) {
                JSONObject popularResult = popular.getJSONObject(i);
                PopularInfo popularInfo = new PopularInfo();
                popularInfo.setName(popularResult.getString("name"));
                popularInfo.setKnown_for_department(popularResult.getString("known_for_department"));
                popularInfo.setProfile_path(popularResult.getString("profile_path"));
                popularInfo.setId(popularResult.getInt("id"));
                popularInfos.add(popularInfo);
            }
            model.controller.conRecycle(popularInfos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}