package com.mona.movies_app.popular_people.popular_list_screen.interfaces

import com.mona.movies_app.popular_people.pojo.PopularInfo

interface ListModelInterface {
    fun getUrl(pageNum: String, loadData: (popularInfo: PopularInfo?)-> Unit )
    fun getSearchList(searchStr: String, loadData: (popularInfo: PopularInfo?)-> Unit )
}