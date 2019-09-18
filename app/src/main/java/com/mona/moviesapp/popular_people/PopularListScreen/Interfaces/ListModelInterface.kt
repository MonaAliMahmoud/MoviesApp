package com.mona.moviesapp.popular_people.PopularListScreen.Interfaces

import com.mona.moviesapp.popular_people.pojo.PopularInfo

interface ListModelInterface {
    fun geturl(popularUrl: String, loadData: (popularInfo: PopularInfo?)-> Unit )
}