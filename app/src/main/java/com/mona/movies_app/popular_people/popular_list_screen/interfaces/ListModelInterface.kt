package com.mona.movies_app.popular_people.popular_list_screen.interfaces

import com.mona.movies_app.popular_people.pojo.PopularInfo

interface ListModelInterface {
    fun getUrl(pageNum: Int, loadData: (popularInfo: PopularInfo?)-> Unit )
}