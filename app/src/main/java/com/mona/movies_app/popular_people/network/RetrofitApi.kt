package com.mona.movies_app.popular_people.network

import com.mona.movies_app.popular_people.pojo.PopularList
import com.mona.movies_app.popular_people.pojo.PopularProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421")
    fun callJson(@Query ("page") page: String): Call<PopularList>

    @GET("search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421")
    fun searching(@Query ("query") searchStr: String): Call<PopularList>

    @GET("person/{popularId}/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421")
    fun getProfiles(@Path ("popularId") popularId: String): Call<PopularProfile>
}