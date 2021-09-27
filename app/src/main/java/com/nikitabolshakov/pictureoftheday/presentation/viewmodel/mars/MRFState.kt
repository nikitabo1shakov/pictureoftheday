package com.nikitabolshakov.pictureoftheday.presentation.viewmodel.mars

import com.nikitabolshakov.pictureoftheday.presentation.model.mars.MRFServerResponseData

sealed class MRFState {
    data class Success(val serverResponseData: MRFServerResponseData) : MRFState()
    data class Error(val error: Throwable) : MRFState()
    data class Loading(val progress: Int?) : MRFState()
}