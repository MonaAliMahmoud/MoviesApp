package com.mona.movies_app.popular_people.popular_details_screen.interfaces

import com.mona.movies_app.popular_people.pojo.Profiles

interface DetailsModelInterface {
    fun getPopProfiles(profileUrl: String, loadProfile: (profile: Profiles?)-> Any)
}