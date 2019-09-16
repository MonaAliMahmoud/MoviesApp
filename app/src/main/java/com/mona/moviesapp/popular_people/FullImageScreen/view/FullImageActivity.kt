package com.mona.moviesapp.popular_people.FullImageScreen.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController

class FullImageActivity : AppCompatActivity() {

    lateinit var full_img: ImageView
    lateinit var saveImg: Button

    var picturePath: String? = ""

    lateinit var fullImageController: FullImageController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        full_img = findViewById(R.id.fullimg)
        saveImg = findViewById(R.id.savebtn)

        fullImageController = FullImageController(this)

        picturePath = fullImageController.setPicturePath()
        fullImageController.getPicturePath(picturePath!!)

        saveImg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@FullImageActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this@FullImageActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
            saveImageToGallery()
        }
    }

    private fun saveImageToGallery() {
        full_img.isDrawingCacheEnabled = true
        val b = full_img.drawingCache
        MediaStore.Images.Media.insertImage(contentResolver, b, picturePath, "")
        Toast.makeText(this@FullImageActivity, "Saved to gallery", Toast.LENGTH_LONG).show()
    }

    fun setImage(bitmap: Bitmap?) {
        if (bitmap != null) {
            full_img.setImageBitmap(bitmap)
        } else {
            full_img.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}
