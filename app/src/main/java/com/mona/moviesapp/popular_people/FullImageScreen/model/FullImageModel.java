package com.mona.moviesapp.popular_people.FullImageScreen.model;

import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.ImageNetwork;

import java.util.concurrent.ExecutionException;

public class FullImageModel {

    FullImageController fullImageController;
    ImageNetwork imageNetwork = new ImageNetwork(this, fullImageController);

    public FullImageModel(FullImageController fullImageController) {
        this.fullImageController = fullImageController;
        this.imageNetwork = imageNetwork;
    }

    public void setPicturePath(String picturePath) {
        try {
            imageNetwork.execute(picturePath).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
