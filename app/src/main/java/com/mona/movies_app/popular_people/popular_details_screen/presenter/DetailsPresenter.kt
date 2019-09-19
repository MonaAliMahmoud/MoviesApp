package com.mona.movies_app.popular_people.popular_details_screen.presenter

import com.mona.movies_app.popular_people.popular_details_screen.interfaces.DetailsModelInterface
import com.mona.movies_app.popular_people.popular_details_screen.interfaces.DetailsViewInterface
import com.mona.movies_app.popular_people.pojo.Profiles

class DetailsPresenter(private var detailsViewInterface: DetailsViewInterface, private var detailsModelInterface: DetailsModelInterface) {

    fun setPopId(popularId: Int) {
        detailsModelInterface.getPopProfiles(popularId.toString()){
            detailsViewInterface.addPopularDetails(it!!)
            detailsViewInterface.changeGrid()
        }
    }

    fun onItemViewClicked(prof: Profiles){
        val imgPath = "https://image.tmdb.org/t/p/w500/"
        detailsViewInterface.goToFullImageScreen(imgPath, prof)
    }
}
