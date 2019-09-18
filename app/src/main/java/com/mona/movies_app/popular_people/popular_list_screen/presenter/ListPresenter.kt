package com.mona.movies_app.popular_people.popular_list_screen.presenter

import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.pojo.PopularInfo

class ListPresenter(private var listViewInterface: ListViewInterface, private var listModelInterface: ListModelInterface) {

    fun callJson(pageNum: Int) {
        val popularUrl = "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page=$pageNum"
        listModelInterface.getUrl(popularUrl){
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }

    fun searchingCall(searchStr: String) {
        val searchUrl = "https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query=$searchStr"
        listModelInterface.getUrl(searchUrl){
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }

    fun onItemViewClicked(popularInf: PopularInfo){
        val imgPath = "https://image.tmdb.org/t/p/w500/"
        listViewInterface.goToDetailsScreen(imgPath, popularInf)
    }
}
