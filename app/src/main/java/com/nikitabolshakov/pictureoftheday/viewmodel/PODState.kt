package com.nikitabolshakov.pictureoftheday.viewmodel

import com.nikitabolshakov.pictureoftheday.model.retrofit.PODServerResponseData

sealed class PODState {
    data class Success(val serverResponseData: PODServerResponseData) : PODState()
    data class Error(val error: Throwable) : PODState()
    data class Loading(val progress: Int?) : PODState()
}