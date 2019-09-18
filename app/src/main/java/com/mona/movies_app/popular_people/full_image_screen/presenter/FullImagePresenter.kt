package com.mona.movies_app.popular_people.full_image_screen.presenter

import com.mona.movies_app.popular_people.full_image_screen.interfaces.FullImageModelInterface
import com.mona.movies_app.popular_people.full_image_screen.interfaces.FullImageViewInterface

class FullImagePresenter(private val fullImageViewInterface: FullImageViewInterface,
                         private val fullImageModelInterface: FullImageModelInterface) {

    fun saveImage(picturePath: String) {
        fullImageModelInterface.setPicturePath(picturePath)
    }
}
