package com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces

import com.mona.moviesapp.popular_people.pojo.Profiles
import java.util.ArrayList

interface DetailsViewInterface {
    fun configGridRecycleview(profiles: ArrayList<Profiles>)
    fun changeGrid()
    fun addPopularDetails(profiles: Profiles)
    fun goToFullImageScreen(imgPath: String, prof: Profiles)
}