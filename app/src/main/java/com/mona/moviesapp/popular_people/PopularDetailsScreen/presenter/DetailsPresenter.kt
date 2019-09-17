package com.mona.moviesapp.popular_people.PopularDetailsScreen.presenter

import com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces.DetailsModelInterface
import com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces.DetailsViewInterface

class DetailsPresenter(var detailsViewInterface: DetailsViewInterface, var detailsModelInterface: DetailsModelInterface) {

    fun setPopId(popularId: Int) {
        val profileUrl = "https://api.themoviedb.org/3/person/$popularId/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421"
        detailsModelInterface.getPopProfiles(profileUrl){
            detailsViewInterface.addPopularDetails(it!!)
            detailsViewInterface.changeGride()
        }
    }
}
