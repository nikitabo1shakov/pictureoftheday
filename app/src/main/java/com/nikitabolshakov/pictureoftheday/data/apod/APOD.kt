package com.nikitabolshakov.pictureoftheday.data.apod

import com.nikitabolshakov.pictureoftheday.presentation.model.apod.APODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APOD {

    @GET("planetary/apod")
    fun getAPOD(
        @Query("date") date: String?,
        @Query("api_key") apiKey: String
    ): Call<APODServerResponseData>
}