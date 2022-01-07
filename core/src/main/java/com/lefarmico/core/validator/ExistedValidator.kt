package com.lefarmico.core.validator

class ExistedValidator(
    private val message: String,
    private val messageList: List<String>
) : Validator() {

    object ExistedState : ValidationState

    override val state: ValidationState = ExistedState

    override fun isValid(): Boolean {
        return messageList.none { msg -> msg == message }
    }
}
