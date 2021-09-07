package com.nikitabolshakov.pictureoftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitabolshakov.pictureoftheday.BuildConfig
import com.nikitabolshakov.pictureoftheday.model.PODRetrofitImpl
import com.nikitabolshakov.pictureoftheday.model.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class PictureOfTheDayState {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
    data class Loading(val progress: Int?) : PictureOfTheDayState()
}

class PictureOfTheDayViewModel(
    private val liveStateForViewToObserve: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayState> {
        sendServerRequest()
        return liveStateForViewToObserve
    }

    private fun sendServerRequest() {
        liveStateForViewToObserve.value = PictureOfTheDayState.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveStateForViewToObserve.value =
                            PictureOfTheDayState.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveStateForViewToObserve.value =
                                PictureOfTheDayState.Error(Throwable("Unidentified error"))
                        } else {
                            liveStateForViewToObserve.value =
                                PictureOfTheDayState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveStateForViewToObserve.value = PictureOfTheDayState.Error(t)
                }
            })
        }
    }
}