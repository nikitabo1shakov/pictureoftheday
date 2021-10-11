package com.nikitabolshakov.pictureoftheday.data.removed.marsroverphotos

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MRFImpl {

    fun getMRFImpl(): MRF {
        val mrfRetrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
        return mrfRetrofit.create(MRF::class.java)
    }
}