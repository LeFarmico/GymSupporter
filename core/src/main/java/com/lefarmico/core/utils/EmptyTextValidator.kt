package com.lefarmico.core.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmptyTextValidator(
    private val editText: TextInputEditText,
    private val inputLayout: TextInputLayout
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (editText.text.toString().trim().isEmpty()) {
            inputLayout.error = "The field must not be empty."
        } else {
            inputLayout.isErrorEnabled = false
        }
    }
}
