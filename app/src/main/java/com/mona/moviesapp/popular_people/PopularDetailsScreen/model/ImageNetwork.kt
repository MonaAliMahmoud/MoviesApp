package com.mona.moviesapp.popular_people.PopularDetailsScreen.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ImageNetwork(internal var detailsModel: DetailsModel, private val detailsController: DetailsController) : AsyncTask<String, Void, Bitmap>() {

    internal var ImageUrl: URL? = null
    internal var bmImg: Bitmap? = null
    internal var inputStream: InputStream? = null

    override fun doInBackground(vararg strings: String): Bitmap? {

        try {
            ImageUrl = URL(strings[0])
            Log.i("URL", ImageUrl!!.toString())

            val conn = ImageUrl!!.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            inputStream = conn.inputStream
            bmImg = BitmapFactory.decodeStream(inputStream)

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmImg
    }

    override fun onPostExecute(bitmap: Bitmap) {
        super.onPostExecute(bitmap)
        detailsModel.detailsController.getImage(bitmap)
    }
}
