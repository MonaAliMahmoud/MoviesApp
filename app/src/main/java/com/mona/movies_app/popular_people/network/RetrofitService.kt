package com.mona.movies_app.popular_people.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val URL = "https://api.themoviedb.org/3/"

    object RetrofitManager {
        private var retrofit: Retrofit? = null
        private val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = retrofit2.Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retrofit
            }

        fun getInstance(): Retrofit? {
            return retrofitInstance
        }
    }
}