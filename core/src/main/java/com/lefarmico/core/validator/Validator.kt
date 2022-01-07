package com.lefarmico.core.validator

abstract class Validator {

    abstract val state: ValidationState

    abstract fun isValid(): Boolean

    fun validate(): ValidationState {
        return when (isValid()) {
            true -> ValidationState.SuccessState
            false -> state
        }
    }
}
