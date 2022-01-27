package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState

sealed class DetailedEvent : BaseState.Event {
    object DataLoadFailure : DetailedEvent()
}
