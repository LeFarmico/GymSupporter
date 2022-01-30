package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class DetailedEvent : BaseState.Event {
    object DataLoadFailure : DetailedEvent()
    object Loading : DetailedEvent()
    data class ExceptionResult(val exception: Exception) : DetailedEvent()
}
