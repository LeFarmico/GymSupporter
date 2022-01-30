package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseIntent

sealed class MainIntent : BaseIntent {

    object LoadPreloadedData : MainIntent()
    data class ShowToast(val text: String) : MainIntent()
}
