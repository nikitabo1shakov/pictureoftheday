package com.nikitabolshakov.pictureoftheday.viewmodel

import com.nikitabolshakov.pictureoftheday.model.api.marsroverphotos.MRFServerResponseData

sealed class MRFState {
    data class Success(val serverResponseData: MRFServerResponseData) : MRFState()
    data class Error(val error: Throwable) : MRFState()
    data class Loading(val progress: Int?) : MRFState()
}