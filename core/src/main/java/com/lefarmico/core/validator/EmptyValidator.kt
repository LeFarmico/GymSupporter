package com.lefarmico.core.validator

class EmptyValidator(private val validateField: String) : Validator() {

    object EmptyState : ValidationState

    override val state: ValidationState = EmptyState

    override fun isValid(): Boolean {
        return validateField.isNotEmpty()
    }
}
