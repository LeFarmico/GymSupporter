package com.lefarmico.core.utils

sealed class ValidationState {

    object Empty : ValidationState()

    data class AlreadyExist(
        val field: String = ""
    ) : ValidationState()

    data class Success(
        val field: String = ""
    ) : ValidationState()
}
