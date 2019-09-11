package com.mona.moviesapp.popular_people.PopularListScreen.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mona.moviesapp.popular_people.PopularListScreen.model.ListModel;
import com.mona.moviesapp.popular_people.PopularListScreen.view.PopularListActivity;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListController {

    private PopularListActivity popularListActivity;
    private ListModel listModel = new ListModel(this);

    public ListController(PopularListActivity popularListActivity) {
        this.popularListActivity = popularListActivity;
        this.listModel = listModel;
    }

    public ListController(ListModel listModel) {
        this.listModel = listModel;
    }

    public ListController(PopularListActivity popularListActivity, ListModel listModel) {
        this.popularListActivity = popularListActivity;
        this.listModel = listModel;
    }

    ArrayList<PopularInfo> popularInfos = new ArrayList<>();
    URL url = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

    public void conRecycle(ArrayList<PopularInfo> popularInfos){

        popularListActivity.configRecycleview(popularInfos);
    }

    public void callJson(int pagenum){
        listModel.geturl(pagenum);
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
        listModel.setListController(this);
    }
}
