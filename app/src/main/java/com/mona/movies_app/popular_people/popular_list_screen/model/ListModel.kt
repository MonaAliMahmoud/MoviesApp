package com.mona.movies_app.popular_people.popular_list_screen.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.mona.movies_app.popular_people.network.RetrofitApi
import com.mona.movies_app.popular_people.network.RetrofitService
import com.mona.movies_app.popular_people.pojo.PopularInfo
import com.mona.movies_app.popular_people.pojo.PopularList
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListModel: ListModelInterface{

    var popularInfo: ArrayList<PopularInfo>? = null

    override fun getUrl(pageNum: Int, loadData: (popularInfo: PopularInfo?) -> Unit) {

        val service = RetrofitService.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
        val call: Call<PopularList>? = service?.callJson(pageNum)

        call?.enqueue(object : Callback<PopularList> {

            var handler: Handler = Handler(Looper.getMainLooper())
            override fun onFailure(call: Call<PopularList>, t: Throwable) {
                Log.i("","Failed to add item")
            }

            override fun onResponse(call: Call<PopularList>, response: Response<PopularList>) {
                if(response.isSuccessful){
                    popularInfo = response.body()!!.results
                    for (i in 0 until popularInfo!!.size)
                    handler.post{
                        loadData(popularInfo!![i])
                    }
                    Log.i("","Successfully Added")
                }
                else{
                    Log.i("","Failed to connect server")
                }
            }
        })
    }
}
