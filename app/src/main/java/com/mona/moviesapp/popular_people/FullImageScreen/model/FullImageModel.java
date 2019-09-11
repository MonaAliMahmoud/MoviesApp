package com.mona.moviesapp.popular_people.FullImageScreen.model;

import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;

import java.util.concurrent.ExecutionException;

public class FullImageModel {

    FullImageController fullImageController;
    ImageNetwork imageNetwork = new ImageNetwork(this, fullImageController);

    public FullImageModel(FullImageController fullImageController) {
        this.fullImageController = fullImageController;
        this.imageNetwork = imageNetwork;
    }

    public void setPicturePath(String picturePath) {
        imageNetwork.execute(picturePath);
    }
}
