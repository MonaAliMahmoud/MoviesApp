package com.mona.moviesapp.popular_people.FullImageScreen.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.mona.moviesapp.popular_people.FullImageScreen.model.FullImageModel;
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

public class FullImageController {

    private FullImageActivity fullImageActivity;
    FullImageModel fullImageModel = new FullImageModel(this);

    private String picturePath;
    private Intent intent;
    private Bundle bundle;
    PopularInfo popularInfo = new PopularInfo();

    public FullImageController(FullImageActivity fullImageActivity) {
        this.fullImageActivity = fullImageActivity;
        this.fullImageModel = fullImageModel;
    }

    public void getPicturePath(String picturePath){
        fullImageModel.setPicturePath(picturePath);
    }

    public void getImage(Bitmap bitmap){
        fullImageActivity.setImage(bitmap);
    }

    public String setPicturePath() {

        intent = fullImageActivity.getIntent();
        bundle =  intent.getBundleExtra("data");

        if(!bundle.isEmpty()) {
            picturePath = bundle.getString("picture_path");
        }
        return picturePath;
    }

}
