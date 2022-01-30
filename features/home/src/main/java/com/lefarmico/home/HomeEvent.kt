package com.lefarmico.home

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class HomeEvent : BaseState.Event {

    object ShowEditState : HomeEvent()
    object HideEditState : HomeEvent()
    object SelectAllWorkouts : HomeEvent()
    object DeselectAllWorkouts : HomeEvent()
    object DeleteSelectedWorkouts : HomeEvent()

    data class ExceptionResult(val exception: Exception) : HomeEvent()
}
