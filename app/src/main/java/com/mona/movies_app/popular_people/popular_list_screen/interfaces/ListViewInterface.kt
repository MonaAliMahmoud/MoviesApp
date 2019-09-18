package com.mona.movies_app.popular_people.popular_list_screen.interfaces

import com.mona.movies_app.popular_people.pojo.PopularInfo
import java.util.ArrayList

interface ListViewInterface {

    fun configRecycleView(popularInfos: ArrayList<PopularInfo>)
    fun changeList()
    fun addPopularList(popularInfo: PopularInfo)
    fun goToDetailsScreen(imgPath: String, popularInf: PopularInfo)
}