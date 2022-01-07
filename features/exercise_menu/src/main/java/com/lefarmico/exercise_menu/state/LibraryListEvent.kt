package com.lefarmico.exercise_menu.state

import com.lefarmico.core.base.BaseState

sealed class LibraryListEvent : BaseState.Event {
    data class ShowToast(val text: String) : LibraryListEvent()
    sealed class ValidationResult : LibraryListEvent() {
        object Empty : ValidationResult()
        object AlreadyExist : ValidationResult()
        object Success : ValidationResult()
    }
}
