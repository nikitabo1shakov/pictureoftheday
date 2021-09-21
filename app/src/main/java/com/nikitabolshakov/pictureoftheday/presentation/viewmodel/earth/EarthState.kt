package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.earth

import com.nikitabolshakov.pictureoftheday.presentation.model.earth.EarthServerResponseData

sealed class EarthState {
    data class Success(val serverResponseData: EarthServerResponseData) : EarthState()
    data class Error(val error: Throwable) : EarthState()
    data class Loading(val progress: Int?) : EarthState()
}