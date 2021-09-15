package com.nikitabolshakov.pictureoftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitabolshakov.pictureoftheday.BuildConfig
import com.nikitabolshakov.pictureoftheday.model.api.earth.EarthImpl
import com.nikitabolshakov.pictureoftheday.model.api.earth.EarthServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val liveStateForViewToObserve: MutableLiveData<EarthState> = MutableLiveData(),
    private val earthImpl: EarthImpl = EarthImpl()
) : ViewModel() {

    fun getData(): LiveData<EarthState> {
        sendServerRequest()
        return liveStateForViewToObserve
    }

    private fun sendServerRequest() {
        liveStateForViewToObserve.value = EarthState.Loading(null)

        val lon = 95.33F
        val lat = 29.78F
        val date = "2021-09-01"
        val dim = 0.10F
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            EarthState.Error(Throwable("You need API key"))
        } else {
            earthImpl.getEarthImpl().getEarth(lon, lat, date, dim, apiKey)
                .enqueue(object : Callback<EarthServerResponseData> {
                    override fun onResponse(
                        call: Call<EarthServerResponseData>,
                        response: Response<EarthServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveStateForViewToObserve.value =
                                EarthState.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveStateForViewToObserve.value =
                                    EarthState.Error(Throwable("Unidentified error"))
                            } else {
                                liveStateForViewToObserve.value =
                                    EarthState.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<EarthServerResponseData>, t: Throwable) {
                        liveStateForViewToObserve.value = EarthState.Error(t)
                    }
                })
        }
    }
}