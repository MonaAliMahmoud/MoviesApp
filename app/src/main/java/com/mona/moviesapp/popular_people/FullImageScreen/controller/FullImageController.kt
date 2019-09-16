package com.mona.moviesapp.popular_people.FullImageScreen.controller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle

import com.mona.moviesapp.popular_people.FullImageScreen.model.FullImageModel
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity

class FullImageController(private val fullImageActivity: FullImageActivity) {
    internal var fullImageModel = FullImageModel(this)
    private var picturePath: String? = null
    private var intent: Intent? = null
    private var bundle: Bundle? = null

    init {
        this.fullImageModel = fullImageModel
    }

    fun getPicturePath(picturePath: String) {
        fullImageModel.setPicturePath(picturePath)
    }

    fun getImage(bitmap: Bitmap) {
        fullImageActivity.setImage(bitmap)
    }

    fun setPicturePath(): String? {

        intent = fullImageActivity.intent
        bundle = intent!!.getBundleExtra("data")

        if (!bundle!!.isEmpty) {
            picturePath = bundle!!.getString("picture_path")
        }
        return picturePath
    }
}
