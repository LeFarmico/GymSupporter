package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState

sealed class DetailedEvent : BaseState.Event {
    data class ShowToast(val text: String) : DetailedEvent()
}
