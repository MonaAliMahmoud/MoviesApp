package com.mona.moviesapp.popular_people.PopularListScreen.model

import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController

class ListModel(internal var listController: ListController) {
    internal var dataNetwork = DataNetwork(this, listController)

    init {
        this.dataNetwork = dataNetwork
    }

    fun geturl(popularurl: String) {
        val dataNetwork = DataNetwork(this, listController)
        dataNetwork.execute(popularurl)
    }
}
