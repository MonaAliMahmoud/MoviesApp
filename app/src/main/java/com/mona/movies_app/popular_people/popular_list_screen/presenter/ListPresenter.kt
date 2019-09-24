package com.mona.movies_app.popular_people.popular_list_screen.presenter

import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.pojo.PopularInfo
import java.util.ArrayList

class ListPresenter(private var listViewInterface: ListViewInterface, private var listModelInterface: ListModelInterface) {

    private val popularInfos = ArrayList<PopularInfo>()

    fun callJson(pageNum: Int) {
        listModelInterface.getUrl(pageNum.toString()) {
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }

    fun searchingCall(searchStr: String) {
        popularInfos.clear()
        listModelInterface.getSearchList(searchStr) {
            listViewInterface.addPopularList(it!!)
            listViewInterface.changeList()
        }
    }

    fun onItemViewClicked(popularInf: PopularInfo) {
        val imgPath = "https://image.tmdb.org/t/p/w500/"
        listViewInterface.goToDetailsScreen(imgPath, popularInf)
    }

    fun loadNextPage(pageNum: Int) {
        val nextPageNum = pageNum +1
        callJson(nextPageNum)
    }

    fun refresh(pageNum: Int){
        popularInfos.clear()
        callJson(pageNum)
//        listModelInterface.getUrl(pageNum.toString()) {
//            listViewInterface.addPopularList(it!!)
//            listViewInterface.changeList()
//        }
    }
}
