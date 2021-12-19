package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.domain.preferences.FormatterPreferenceHelper
import javax.inject.Inject

class FormatterPreferenceHelperImpl @Inject constructor(
    context: Context
) : FormatterPreferenceHelper {

    private val preference = context.getSharedPreferences(FORMATTER_FILE, Context.MODE_PRIVATE)

    override fun getFormatterRes(): Int {
        return preference.getInt(FORMATTER_PREFERENCE_KEY, 0)
    }

    override fun setFormatterRes(formatterId: Int) {
        preference.edit().putInt(FORMATTER_PREFERENCE_KEY, formatterId).apply()
    }

    companion object {

        const val FORMATTER_PREFERENCE_KEY = "FORMATTER_PREFERENCE_KEY"
        const val FORMATTER_FILE = "FORMATTER_FILE"
    }
}
