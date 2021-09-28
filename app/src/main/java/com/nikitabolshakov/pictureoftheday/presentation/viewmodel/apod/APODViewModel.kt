package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.apod

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitabolshakov.pictureoftheday.BuildConfig
import com.nikitabolshakov.pictureoftheday.data.removed.apod.APODImpl
import com.nikitabolshakov.pictureoftheday.presentation.model.apod.Day
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

private const val NASA_TIME_ZONE = "America/Los_Angeles"

class APODViewModel(
    private val liveStateForViewToObserve: MutableLiveData<APODState> = MutableLiveData(),
    private val apodImpl: APODImpl = APODImpl()
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataToday(): LiveData<APODState> {
        sendServerRequest(Day.TODAY)
        return liveStateForViewToObserve
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataYesterday(): LiveData<APODState> {
        sendServerRequest(Day.YESTERDAY)
        return liveStateForViewToObserve
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataDayBeforeYesterday(): LiveData<APODState> {
        sendServerRequest(Day.DAY_BEFORE_YESTERDAY)
        return liveStateForViewToObserve
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendServerRequest(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            liveStateForViewToObserve.postValue(APODState.Loading(null))
            try {
                val response =
                    apodImpl.getAPODImpl().getAPOD(getDate(day), BuildConfig.NASA_API_KEY)
                liveStateForViewToObserve.postValue(APODState.Success(response))
            } catch (e: Exception) {
                liveStateForViewToObserve.postValue(APODState.Error(e))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(day: Day): String {
        val todayInUSA = LocalDate.now(ZoneId.of(NASA_TIME_ZONE))
        return when (day) {
            Day.TODAY -> todayInUSA.toString()
            Day.YESTERDAY -> todayInUSA.minusDays(1).toString()
            Day.DAY_BEFORE_YESTERDAY -> todayInUSA.minusDays(2).toString()
        }
    }
}