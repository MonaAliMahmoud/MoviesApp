package com.mona.moviesapp.popular_people.PopularListScreen.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mona.moviesapp.popular_people.PopularListScreen.model.DataNetwork;
import com.mona.moviesapp.popular_people.PopularListScreen.model.Model;
import com.mona.moviesapp.popular_people.PopularListScreen.view.PopularListActivity;
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
import java.util.concurrent.ExecutionException;

public class Controller {

    private PopularListActivity popularListActivity;
    private Model model = new Model(this);

    public Controller(PopularListActivity popularListActivity) {
        this.popularListActivity = popularListActivity;
        this.model = model;
    }

    public Controller(Model model) {
        this.model = model;
    }

    public Controller(PopularListActivity popularListActivity, Model model) {
        this.popularListActivity = popularListActivity;
        this.model = model;
    }

    ArrayList<PopularInfo> popularInfos = new ArrayList<>();
    URL url = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

//    public void created(){
//        model.getDataNetwork().execute();
//    }

    public void conRecycle(){
        popularListActivity.configrecycleview();
    }

    public void callJson(int pagenum){
        model.geturl(pagenum);
    }

    public Bitmap loadImage(String urls){
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(urls);
            Log.i("URL",url.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            bmImg = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmImg;
    }

    public void getModel(){
        model.setController(this);
    }
}
