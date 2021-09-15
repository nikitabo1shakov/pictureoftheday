package com.nikitabolshakov.pictureoftheday.model.api.earth

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthImpl {

    fun getEarthImpl(): Earth {
        val earthRetrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
        return earthRetrofit.create(Earth::class.java)
    }
}