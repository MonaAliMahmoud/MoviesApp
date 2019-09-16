package com.mona.moviesapp.popular_people.PopularDetailsScreen.controller

import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.DetailsModel
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity
import com.mona.moviesapp.popular_people.pojo.Profiles

class DetailsController(private val popularDetailsActivity: PopularDetailsActivity) {
    private var detailsModel = DetailsModel(this)

    init {
        this.detailsModel = detailsModel
    }

    fun getImage(bitmap: Any) {
        popularDetailsActivity.setImage(bitmap)
    }

    fun conGridRecycle() {
        popularDetailsActivity.changeGride()
    }

    fun setPopId(popularId: Int) {
        val profileUrl = "https://api.themoviedb.org/3/person/$popularId/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421"
        detailsModel.getPopProfiles(profileUrl)
    }

    fun getPopularDetails(profiles: Profiles) {
        popularDetailsActivity.addPopularDetails(profiles)
    }

    fun getPopImage(popProfile: String) {
        detailsModel.setPopImage(popProfile)
    }
}
