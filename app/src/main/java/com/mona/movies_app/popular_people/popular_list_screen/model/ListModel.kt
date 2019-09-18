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
    private val service = RetrofitService.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)

    override fun getUrl(pageNum: String, loadData: (popularInfo: PopularInfo?) -> Unit) {

        val callJson: Call<PopularList>? = service?.callJson(pageNum)

        callJson?.enqueue(object : Callback<PopularList> {

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

    override fun getSearchList(searchStr: String, loadData: (popularInfo: PopularInfo?) -> Unit) {

        val search: Call<PopularList>? = service?.searching(searchStr)

        search?.enqueue(object : Callback<PopularList> {

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
