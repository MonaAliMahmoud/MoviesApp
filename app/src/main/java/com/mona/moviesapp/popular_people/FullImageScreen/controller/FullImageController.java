package com.mona.moviesapp.popular_people.FullImageScreen.controller;

import android.graphics.Bitmap;

import com.mona.moviesapp.popular_people.FullImageScreen.model.FullImageModel;
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity;

public class FullImageController {

    private FullImageActivity fullImageActivity;
    FullImageModel fullImageModel = new FullImageModel(this);

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

}
