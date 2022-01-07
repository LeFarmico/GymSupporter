package com.lefarmico.core.validator

sealed interface ValidationState {
    object SuccessState : ValidationState
}
