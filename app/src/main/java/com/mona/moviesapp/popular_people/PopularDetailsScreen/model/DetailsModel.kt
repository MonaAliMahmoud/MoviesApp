package com.mona.moviesapp.popular_people.PopularDetailsScreen.model

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController

class DetailsModel(internal var detailsController: DetailsController) {
    internal var imageNetwork = ImageNetwork(this, detailsController)
    internal var profileImagesNetwork = ProfileImagesNetwork(this, detailsController)

    init {
        this.imageNetwork = imageNetwork
        this.profileImagesNetwork = profileImagesNetwork
    }

    fun getPopProfiles(profileUrl: String) {
        val profileImagesNetwork = ProfileImagesNetwork(this, detailsController)
        profileImagesNetwork.execute(profileUrl)
    }

    fun setPopImage(popProfile: String) {
        val imageNetwork = ImageNetwork(this, detailsController)
        imageNetwork.execute(popProfile)
    }
}
