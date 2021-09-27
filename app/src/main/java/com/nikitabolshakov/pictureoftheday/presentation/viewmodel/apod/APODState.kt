package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.apod

import com.nikitabolshakov.pictureoftheday.presentation.model.apod.APODServerResponseData

sealed class APODState {
    data class Success(val serverResponseData: APODServerResponseData) : APODState()
    data class Error(val error: Throwable) : APODState()
    data class Loading(val progress: Int?) : APODState()
}