package com.mona.moviesapp.popular_people.PopularDetailsScreen.controller;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.DetailsModel;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity;
import com.mona.moviesapp.popular_people.pojo.Profiles;

public class DetailsController {

    private PopularDetailsActivity popularDetailsActivity;
    private DetailsModel detailsModel = new DetailsModel(this);

    public DetailsController(PopularDetailsActivity popularDetailsActivity) {
        this.popularDetailsActivity = popularDetailsActivity;
        this.detailsModel = detailsModel;
    }

    public void getImage(Object bitmap){
        popularDetailsActivity.setImage(bitmap);
    }

    public void conGridRecycle(){
        popularDetailsActivity.changeGride();
    }

    public void setPopId(int popularId){
        String profileUrl = "https://api.themoviedb.org/3/person/"+popularId+"/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421";
        detailsModel.getPopProfiles(profileUrl);
    }

    public void getPopularDetails(Profiles profiles){
        popularDetailsActivity.addPopularDetails(profiles);
    }

    public void getPopImage(String popProfile){
        detailsModel.setPopImage(popProfile);
    }
}
