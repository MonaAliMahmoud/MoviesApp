package com.mona.moviesapp.popular_people.FullImageScreen.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.Interfaces.FullImageViewInterface
import com.mona.moviesapp.popular_people.FullImageScreen.model.FullImageModel
import com.mona.moviesapp.popular_people.FullImageScreen.presenter.FullImagePresenter

class FullImageActivity : AppCompatActivity(), FullImageViewInterface {

    lateinit var full_img: ImageView
    lateinit var saveImg: Button

    private var detailsIntent: Intent? = null
    private var bundle: Bundle? = null

    var picturePath: String? = ""

    lateinit var fullImagePresenter: FullImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        full_img = findViewById(R.id.fullimg)
        saveImg = findViewById(R.id.savebtn)

        fullImagePresenter = FullImagePresenter(this, FullImageModel())

        detailsIntent = intent
        bundle = intent!!.getBundleExtra("data")

        if (!bundle!!.isEmpty) {
            picturePath = bundle!!.getString("picture_path")
        }

        Glide.with(this)
                .load(picturePath)
                .into(full_img)

        saveImg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@FullImageActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this@FullImageActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
//            fullImagePresenter.saveImage(picturePath!!)
            saveImageToGallery()
        }
    }

    override fun saveImageToGallery() {
        full_img.isDrawingCacheEnabled = true
        val b = full_img.drawingCache
        MediaStore.Images.Media.insertImage(contentResolver, b, picturePath, "")
        Toast.makeText(this@FullImageActivity, "Saved to gallery", Toast.LENGTH_LONG).show()
    }
}
