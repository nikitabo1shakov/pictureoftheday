package com.nikitabolshakov.pictureoftheday.data.removed.apod

import com.nikitabolshakov.pictureoftheday.presentation.model.apod.APODServerResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface APOD {

    @GET("planetary/apod")
    suspend fun getAPOD(
        @Query("date") date: String?,
        @Query("api_key") apiKey: String
    ): APODServerResponseData
}