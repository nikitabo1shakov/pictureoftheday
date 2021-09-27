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
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val NASA_TIME_ZONE = "America/Los_Angeles"

class APODViewModel(
    private val liveStateForViewToObserve: MutableLiveData<APODState> = MutableLiveData(),
    private val apodImpl: APODImpl = APODImpl()
) : ViewModel() {

    // @RequiresApi(Build.VERSION_CODES.O)
    fun getDataToday(): LiveData<APODState> {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(NASA_TIME_ZONE))
        calendar.add(Calendar.DAY_OF_YEAR, 0)
        val date: String? = simpleDateFormat.format(calendar.time)
        sendServerRequest(date) // LocalDate.now().minusDays(2).toString()
        return liveStateForViewToObserve
    }

    fun getDataYesterday(): LiveData<APODState> {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(NASA_TIME_ZONE))
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val date: String? = simpleDateFormat.format(calendar.time)
        sendServerRequest(date)
        return liveStateForViewToObserve
    }

    fun getDataDayBeforeYesterday(): LiveData<APODState> {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(NASA_TIME_ZONE))
        calendar.add(Calendar.DAY_OF_YEAR, -2)
        val date: String? = simpleDateFormat.format(calendar.time)
        sendServerRequest(date)
        return liveStateForViewToObserve
    }

    private fun sendServerRequest(date: String?) {
        liveStateForViewToObserve.value = APODState.Loading(null)
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