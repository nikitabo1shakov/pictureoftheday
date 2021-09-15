package com.nikitabolshakov.pictureoftheday.viewmodel

import com.nikitabolshakov.pictureoftheday.model.api.apod.APODServerResponseData

sealed class APODState {
    data class Success(val serverResponseData: APODServerResponseData) : APODState()
    data class Error(val error: Throwable) : APODState()
    data class Loading(val progress: Int?) : APODState()
}