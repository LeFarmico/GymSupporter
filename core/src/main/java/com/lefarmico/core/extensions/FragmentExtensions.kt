package com.lefarmico.core.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideSoftKeyboard() {
    val activity = this.requireActivity()
    (activity.getSystemService((Context.INPUT_METHOD_SERVICE)) as InputMethodManager).apply {
        hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}
