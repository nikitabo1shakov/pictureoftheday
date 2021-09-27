package com.nikitabolshakov.pictureoftheday.data.marsroverphotos

import com.nikitabolshakov.pictureoftheday.presentation.model.mars.MRFServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MRF {

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMRF(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): Call<MRFServerResponseData>
}