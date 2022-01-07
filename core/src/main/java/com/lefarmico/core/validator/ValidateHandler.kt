package com.lefarmico.core.validator

class ValidateHandler {

    private val validatorList: MutableList<Validator> = mutableListOf()

    fun addValidator(validator: Validator) {
        validatorList.add(validator)
    }

    fun resetValidators() {
        validatorList.clear()
    }

    fun validate(): ValidationState {
        return validatorList.find {
            !it.isValid()
        }?.validate() ?: ValidationState.SuccessState
    }
}
