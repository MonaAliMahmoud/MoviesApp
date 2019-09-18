package com.mona.movies_app.popular_people.full_image_screen.view

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
import com.mona.movies_app.R
import com.mona.movies_app.popular_people.full_image_screen.interfaces.FullImageViewInterface
import com.mona.movies_app.popular_people.full_image_screen.model.FullImageModel
import com.mona.movies_app.popular_people.full_image_screen.presenter.FullImagePresenter

class FullImageActivity : AppCompatActivity(), FullImageViewInterface {

    private lateinit var fullImg: ImageView
    private lateinit var saveImg: Button

    private var detailsIntent: Intent? = null
    private var bundle: Bundle? = null

    private var picturePath: String? = ""

    private lateinit var fullImagePresenter: FullImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        fullImg = findViewById(R.id.full_img)
        saveImg = findViewById(R.id.btn_save)

        fullImagePresenter = FullImagePresenter(this, FullImageModel())

        detailsIntent = intent
        bundle = intent!!.getBundleExtra("data")

        if (!bundle!!.isEmpty) {
            picturePath = bundle!!.getString("picture_path")
        }

        Glide.with(this)
                .load(picturePath)
                .into(fullImg)

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
        fullImg.isDrawingCacheEnabled = true
        val b = fullImg.drawingCache
        MediaStore.Images.Media.insertImage(contentResolver, b, picturePath, "")
        Toast.makeText(this@FullImageActivity, "Saved to gallery", Toast.LENGTH_LONG).show()
    }
}
