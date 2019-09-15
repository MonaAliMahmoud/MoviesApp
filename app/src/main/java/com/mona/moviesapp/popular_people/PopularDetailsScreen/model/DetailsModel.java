package com.mona.moviesapp.popular_people.PopularDetailsScreen.model;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController;

public class DetailsModel {
    DetailsController detailsController;
    ImageNetwork imageNetwork = new ImageNetwork(this, detailsController);
    ProfileImagesNetwork profileImagesNetwork = new ProfileImagesNetwork(this, detailsController);

    public DetailsModel(DetailsController detailsController) {
        this.detailsController = detailsController;
        this.imageNetwork = imageNetwork;
        this.profileImagesNetwork = profileImagesNetwork;
    }

    public void getPopProfiles(String profileUrl){
        ProfileImagesNetwork profileImagesNetwork = new ProfileImagesNetwork(this, detailsController);
        profileImagesNetwork.execute(profileUrl);
    }

    public void setPopImage(String popProfile){
        ImageNetwork imageNetwork = new ImageNetwork(this, detailsController);
        imageNetwork.execute(popProfile);
    }
}
