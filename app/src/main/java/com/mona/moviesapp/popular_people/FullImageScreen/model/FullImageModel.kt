package com.mona.moviesapp.popular_people.FullImageScreen.model

import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController

class FullImageModel(internal var fullImageController: FullImageController) {
    internal var fullImageNetwork = FullImageNetwork(this, fullImageController)

    init {
        this.fullImageNetwork = fullImageNetwork
    }

    fun setPicturePath(picturePath: String) {
        fullImageNetwork.execute(picturePath)
    }
}
