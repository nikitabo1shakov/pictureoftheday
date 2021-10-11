package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitabolshakov.pictureoftheday.BuildConfig
import com.nikitabolshakov.pictureoftheday.data.removed.marsroverphotos.MRFImpl
import com.nikitabolshakov.pictureoftheday.presentation.model.mars.MRFServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MRFViewModel(
    private val liveStateForViewToObserve: MutableLiveData<MRFState> = MutableLiveData(),
    private val mrfImpl: MRFImpl = MRFImpl()
) : ViewModel() {

    fun getData(): LiveData<MRFState> {
        sendServerRequest()
        return liveStateForViewToObserve
    }

    private fun sendServerRequest() {
        liveStateForViewToObserve.value = MRFState.Loading(null)
        val sol = 1000
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MRFState.Error(Throwable("You need API key"))
        } else {
            mrfImpl.getMRFImpl().getMRF(sol, apiKey)
                .enqueue(object : Callback<MRFServerResponseData> {
                    override fun onResponse(
                        call: Call<MRFServerResponseData>,
                        response: Response<MRFServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveStateForViewToObserve.value =
                                MRFState.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveStateForViewToObserve.value =
                                    MRFState.Error(Throwable("Unidentified error"))
                            } else {
                                liveStateForViewToObserve.value =
                                    MRFState.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<MRFServerResponseData>, t: Throwable) {
                        liveStateForViewToObserve.value = MRFState.Error(t)
                    }
                })
        }
    }
}