package com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces

import com.mona.moviesapp.popular_people.pojo.Profiles
import java.util.ArrayList

interface DetailsViewInterface {
    fun configGridRecycleview(profiles: ArrayList<Profiles>)
    fun changeGride()
    fun addPopularDetails(profiles: Profiles)
}