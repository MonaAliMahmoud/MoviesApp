package com.mona.moviesapp.popular_people.PopularDetailsScreen.model

import android.os.AsyncTask

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController
import com.mona.moviesapp.popular_people.pojo.Profiles

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ProfileImagesNetwork(internal var detailsModel: DetailsModel, internal var detailsController: DetailsController) : AsyncTask<String, String, String>() {

    override fun doInBackground(vararg urls: String): String? {

        var httpURLConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(urls[0])
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()

            val stream = httpURLConnection.inputStream
            reader = BufferedReader(InputStreamReader(stream))
            val buffer = StringBuffer()

            do {
                var line = reader.readLine()
                buffer.append(line)
            }while (line != null)

            return buffer.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return null
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        try {
            val popular_profiles = JSONObject(result)
            val pop_profiles = popular_profiles.getJSONArray("profiles")
            for (i in 0 until pop_profiles.length()) {
                val profileResult = pop_profiles.getJSONObject(i)
                val pictures = Profiles()
                pictures.file_path = profileResult.getString("file_path")
                detailsModel.detailsController.getPopularDetails(pictures)
            }
            detailsModel.detailsController.conGridRecycle()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}

