package com.mona.movies_app.popular_people.popular_details_screen.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.mona.movies_app.popular_people.network.RetrofitApi
import com.mona.movies_app.popular_people.network.RetrofitService
import com.mona.movies_app.popular_people.pojo.PopularProfile
import com.mona.movies_app.popular_people.pojo.Profiles
import com.mona.movies_app.popular_people.popular_details_screen.interfaces.DetailsModelInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsModel: DetailsModelInterface{

    var profileList: ArrayList<Profiles>? = null
    private val service = RetrofitService.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)

    override fun getPopProfiles(popularId: String, loadProfile: (profile: Profiles?) -> Unit) {

        val popProfile: Call<PopularProfile>? = service?.getProfiles(popularId)

        popProfile?.enqueue(object : Callback<PopularProfile> {

            var handler: Handler = Handler(Looper.getMainLooper())
            override fun onFailure(call: Call<PopularProfile>, t: Throwable) {
                Log.i("","Failed to add item")
            }

            override fun onResponse(call: Call<PopularProfile>, response: Response<PopularProfile>) {
                if(response.isSuccessful) {
                    profileList = response.body()!!.profiles
                    for (i in 0 until profileList!!.size) {
                        handler.post {
                            loadProfile(profileList!![i])
                        }
                        Log.i("", "Successfully Added")
                    }
                }
                else{
                    Log.i("","Failed to connect server")
                }
            }
        })
    }
}
