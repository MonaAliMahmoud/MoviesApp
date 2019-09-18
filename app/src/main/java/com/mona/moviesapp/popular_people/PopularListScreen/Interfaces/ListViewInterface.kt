package com.mona.moviesapp.popular_people.PopularListScreen.Interfaces

import com.mona.moviesapp.popular_people.pojo.PopularInfo
import java.util.ArrayList

interface ListViewInterface {

    fun configRecycleView(popularInfos: ArrayList<PopularInfo>)
    fun changeList()
    fun addPopularList(popularInfo: PopularInfo)
    fun goToDetailsScreen(imgPath: String, popularInf: PopularInfo)
}