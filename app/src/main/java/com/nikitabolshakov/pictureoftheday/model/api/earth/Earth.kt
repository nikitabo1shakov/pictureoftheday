package com.nikitabolshakov.pictureoftheday.model.api.earth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Earth {

    @GET("planetary/earth/assets")
    fun getEarth(
        @Query("lon") lon: Float,
        @Query("lat") lat: Float,
        @Query("date") date: String,
        @Query("dim") dim: Float,
        @Query("apiKey") apiKey: String
    ): Call<EarthServerResponseData>
}