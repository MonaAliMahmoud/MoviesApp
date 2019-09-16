package com.mona.moviesapp.popular_people.PopularListScreen.controller

import com.mona.moviesapp.popular_people.PopularListScreen.model.ListModel
import com.mona.moviesapp.popular_people.PopularListScreen.view.PopularListActivity
import com.mona.moviesapp.popular_people.pojo.PopularInfo

class ListController(private val popularListActivity: PopularListActivity) {
    private var listModel = ListModel(this)

    internal var img_path = "https://image.tmdb.org/t/p/w500/"

    init {
        this.listModel = listModel
    }

    fun changeAdapter() {
        popularListActivity.changeList()
    }

    fun getPopularList(popularInfo: PopularInfo) {
        popularListActivity.addPopularList(popularInfo)
    }

    fun callJson(pagenum: Int) {
        val popularUrl = "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page=$pagenum"
        listModel.geturl(popularUrl)
    }

    fun searchingCall(searchStr: String) {
        val searchUrl = "https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query=$searchStr"
        listModel.geturl(searchUrl)
    }
}
