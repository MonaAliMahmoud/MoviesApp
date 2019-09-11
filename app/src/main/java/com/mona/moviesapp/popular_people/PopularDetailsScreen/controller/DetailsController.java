package com.mona.moviesapp.popular_people.PopularDetailsScreen.controller;

import android.graphics.Bitmap;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.DetailsModel;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity;
import com.mona.moviesapp.popular_people.pojo.Profiles;

import java.util.ArrayList;

public class DetailsController {

    private PopularDetailsActivity popularDetailsActivity;
    private DetailsModel detailsModel = new DetailsModel(this);

    public DetailsController(PopularDetailsActivity popularDetailsActivity) {
        this.popularDetailsActivity = popularDetailsActivity;
        this.detailsModel = detailsModel;
    }

    public void getImage(Bitmap bitmap){
        popularDetailsActivity.setImage(bitmap);
    }

    public void conGridRecycle(ArrayList<Profiles> profiles){
        popularDetailsActivity.configGridRecycleview(profiles);
    }

    public void setPopId(int popularId){
       detailsModel.getPopId(popularId);
    }
}
