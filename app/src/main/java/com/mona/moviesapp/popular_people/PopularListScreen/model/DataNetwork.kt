package com.mona.moviesapp.popular_people.PopularListScreen.model

import android.os.AsyncTask

import com.mona.moviesapp.popular_people.PopularListScreen.Presenter.ListPresenter
import com.mona.moviesapp.popular_people.pojo.PopularInfo

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DataNetwork(internal var listModel: ListModel) : AsyncTask<String, String, String>() {

    var listPresenter: ListPresenter? = null
    init {
        this.listPresenter = listPresenter
    }

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
            val popularList = JSONObject(result)
            val popular = popularList.getJSONArray("results")
            for (i in 0 until popular.length()) {
                val popularResult = popular.getJSONObject(i)
                val popularInfo = PopularInfo()
                popularInfo.name = popularResult.getString("name")
                popularInfo.known_for_department = popularResult.getString("known_for_department")
                popularInfo.profile_path = popularResult.getString("profile_path")
                popularInfo.id = popularResult.getInt("id")
//                listModel.listPresenter!!.getPopularList(popularInfo)
            }
//            listModel.listPresenter!!.changeAdapter()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}