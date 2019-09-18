package com.mona.movies_app.popular_people.popular_details_screen.interfaces

import com.mona.movies_app.popular_people.pojo.Profiles
import java.util.ArrayList

interface DetailsViewInterface {
    fun configGridRecycleView(profiles: ArrayList<Profiles>)
    fun changeGrid()
    fun addPopularDetails(profiles: Profiles)
    fun goToFullImageScreen(imgPath: String, prof: Profiles)
}