package com.lefarmico.core.validator

class EmptyValidator(private val message: String) : Validator() {

    object EmptyState : ValidationState

    override val state: ValidationState = EmptyState

    override fun isValid(): Boolean {
        return message.isNotEmpty()
    }
}
