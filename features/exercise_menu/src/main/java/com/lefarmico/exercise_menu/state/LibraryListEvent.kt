package com.lefarmico.exercise_menu.state

import com.lefarmico.core.base.BaseState
import java.lang.Exception

sealed class LibraryListEvent : BaseState.Event {
    sealed class ValidationResult : LibraryListEvent() {
        object Empty : ValidationResult()
        object AlreadyExist : ValidationResult()
        object Success : ValidationResult()
    }
    data class ExceptionResult(val exception: Exception) : LibraryListEvent()

    object ShowEditState : LibraryListEvent()
    object HideEditState : LibraryListEvent()
    object SelectAllWorkouts : LibraryListEvent()
    object DeselectAllWorkouts : LibraryListEvent()
    object DeleteSelectedWorkouts : LibraryListEvent()
}
