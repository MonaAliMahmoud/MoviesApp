package com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces

import com.mona.moviesapp.popular_people.pojo.Profiles

interface DetailsModelInterface {
    fun getPopProfiles(profileUrl: String, loadProfile: (profile: Profiles?)-> Any)
}