package com.mona.moviesapp.popular_people.FullImageScreen.model;

import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;

public class FullImageModel {

    FullImageController fullImageController;
    FullImageNetwork fullImageNetwork = new FullImageNetwork(this, fullImageController);

    public FullImageModel(FullImageController fullImageController) {
        this.fullImageController = fullImageController;
        this.fullImageNetwork = fullImageNetwork;
    }

    public void setPicturePath(String picturePath) {
        fullImageNetwork.execute(picturePath);
    }
}
