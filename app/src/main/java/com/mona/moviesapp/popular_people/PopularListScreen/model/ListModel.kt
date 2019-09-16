package com.mona.moviesapp.popular_people.PopularListScreen.model

import com.mona.moviesapp.popular_people.PopularListScreen.Interfaces.ListModelInterface
import com.mona.moviesapp.popular_people.PopularListScreen.Presenter.ListPresenter

class ListModel: ListModelInterface {

    var listPresenter: ListPresenter? = null
    init {
        this.listPresenter = listPresenter
    }

    var dataNetwork = DataNetwork(this)

    init {
        this.dataNetwork = dataNetwork
    }

    override fun geturl(popularUrl: String) {
        val dataNetwork = DataNetwork(this)
        dataNetwork.execute(popularUrl)
    }
}
