package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitabolshakov.pictureoftheday.BuildConfig
import com.nikitabolshakov.pictureoftheday.data.apod.APODImpl
import com.nikitabolshakov.pictureoftheday.presentation.model.apod.APODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APODViewModel(
    private val liveStateForViewToObserve: MutableLiveData<APODState> = MutableLiveData(),
    private val apodImpl: APODImpl = APODImpl()
) : ViewModel() {

    fun getData(): LiveData<APODState> {
        sendServerRequest()
        return liveStateForViewToObserve
    }

    private fun sendServerRequest() {
        liveStateForViewToObserve.value = APODState.Loading(null)
        val date = null
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            APODState.Error(Throwable("You need API key"))
        } else {
            apodImpl.getAPODImpl().getAPOD(date, apiKey)
                .enqueue(object :
                    Callback<APODServerResponseData> {
                    override fun onResponse(
                        call: Call<APODServerResponseData>,
                        response: Response<APODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveStateForViewToObserve.value =
                                APODState.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveStateForViewToObserve.value =
                                    APODState.Error(Throwable("Unidentified error"))
                            } else {
                                liveStateForViewToObserve.value =
                                    APODState.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<APODServerResponseData>, t: Throwable) {
                        liveStateForViewToObserve.value = APODState.Error(t)
                    }
                })
        }
    }
}