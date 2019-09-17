package com.mona.moviesapp.popular_people.PopularDetailsScreen.model

import android.os.Handler
import android.os.Looper
import com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces.DetailsModelInterface
import com.mona.moviesapp.popular_people.pojo.Profiles
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DetailsModel: DetailsModelInterface{

    override fun getPopProfiles(profileUrl: String, loadProfile: (profile: Profiles?) -> Any) {

        val client = OkHttpClient()
        val request = Request.Builder()
                .url(profileUrl)
                .build()

        client.newCall(request).enqueue(object : Callback {
            var handler: Handler = Handler(Looper.getMainLooper())

            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    val result = response.body()!!.string()
                    val popular_profiles = JSONObject(result)
                    val pop_profiles = popular_profiles.getJSONArray("profiles")
                    for (i in 0 until pop_profiles.length()) {
                        val profileResult = pop_profiles.getJSONObject(i)
                        val pictures = Profiles()
                        pictures.file_path = profileResult.getString("file_path")

                        handler.post{
                            loadProfile(pictures)
                        }
                    }
                }
            }
        })
    }
}
