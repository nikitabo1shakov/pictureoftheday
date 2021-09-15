package com.nikitabolshakov.pictureoftheday.viewmodel

import com.nikitabolshakov.pictureoftheday.model.api.earth.EarthServerResponseData

sealed class EarthState {
    data class Success(val serverResponseData: EarthServerResponseData) : EarthState()
    data class Error(val error: Throwable) : EarthState()
    data class Loading(val progress: Int?) : EarthState()
}