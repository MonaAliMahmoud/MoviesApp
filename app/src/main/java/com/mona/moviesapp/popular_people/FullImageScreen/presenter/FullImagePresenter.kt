package com.mona.moviesapp.popular_people.FullImageScreen.presenter

import com.mona.moviesapp.popular_people.FullImageScreen.Interfaces.FullImageModelInterface
import com.mona.moviesapp.popular_people.FullImageScreen.Interfaces.FullImageViewInterface

class FullImagePresenter(private val fullImageViewInterface: FullImageViewInterface,
                         private val fullImageModelInterface: FullImageModelInterface) {

    fun saveImage(picturePath: String) {
        fullImageModelInterface.setPicturePath(picturePath)
    }
}
