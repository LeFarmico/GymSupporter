package com.lefarmico.core.utils

sealed class ValidationState {

    object Empty : ValidationState() {
        const val exception = "field must not be empty"
    }

    data class AlreadyExist(
        val field: String = ""
    ) : ValidationState()

    data class Success(
        val field: String = ""
    ) : ValidationState()
}
