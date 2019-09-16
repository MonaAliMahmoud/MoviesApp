package com.mona.moviesapp.popular_people.PopularListScreen.model

import android.os.Looper
import com.mona.moviesapp.popular_people.PopularListScreen.Interfaces.ListModelInterface
import com.mona.moviesapp.popular_people.pojo.PopularInfo
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.os.Handler

class ListModel: ListModelInterface{

    override fun geturl(popularUrl: String, loadData: (popularInfo: PopularInfo?) -> Any) {

        val client = OkHttpClient()
        val request = Request.Builder()
                .url(popularUrl)
                .build()

        client.newCall(request).enqueue(object : Callback {
            var handler: Handler = Handler(Looper.getMainLooper())

            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    val result = response.body()!!.string()
                    val popularList = JSONObject(result)
                    val popular = popularList.getJSONArray("results")
                    for (i in 0 until popular.length()) {
                        val popularResult = popular.getJSONObject(i)
                        val popularInfo = PopularInfo()
                        popularInfo.name = popularResult.getString("name")
                        popularInfo.known_for_department = popularResult.getString("known_for_department")
                        popularInfo.profile_path = popularResult.getString("profile_path")
                        popularInfo.id = popularResult.getInt("id")

                        handler.post{
                            loadData(popularInfo)
                        }
                    }
                }
            }
        })
    }
}
