package com.mona.moviesapp.popular_people.PopularDetailsScreen.model;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController;

public class DetailsModel {
    DetailsController detailsController;
    ImageNetwork imageNetwork = new ImageNetwork(this, detailsController);

    public DetailsModel(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    public void getPopId(int popid){
        String profileUrl = "https://api.themoviedb.org/3/person/"+popid+"/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421";
        imageNetwork.execute(profileUrl);
    }

}
