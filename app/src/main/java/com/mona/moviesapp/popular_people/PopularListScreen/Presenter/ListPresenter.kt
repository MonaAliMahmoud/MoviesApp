package com.mona.moviesapp.popular_people.PopularListScreen.Presenter

import com.mona.moviesapp.popular_people.PopularListScreen.Interfaces.ListModelInterface
import com.mona.moviesapp.popular_people.PopularListScreen.Interfaces.ListViewInterface
import com.mona.moviesapp.popular_people.pojo.PopularInfo

class ListPresenter(var listViewInterface: ListViewInterface, var listModelInterface: ListModelInterface) {

    internal var img_path = "https://image.tmdb.org/t/p/w500/"

    fun changeAdapter() {
        listViewInterface.changeList()
    }

    fun getPopularList(popularInfo: PopularInfo) {
        listViewInterface.addPopularList(popularInfo)
    }

    fun callJson(pagenum: Int) {
        val popularUrl = "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page=$pagenum"
        listModelInterface.geturl(popularUrl){
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }

    fun searchingCall(searchStr: String) {
        val searchUrl = "https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query=$searchStr"
        listModelInterface.geturl(searchUrl){
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }
}
