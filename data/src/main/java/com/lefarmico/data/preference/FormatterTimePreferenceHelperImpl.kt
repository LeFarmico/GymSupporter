package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.domain.preferences.FormatterTimePreferenceHelper
import javax.inject.Inject

class FormatterTimePreferenceHelperImpl @Inject constructor(
    context: Context
) : FormatterTimePreferenceHelper {

    private val preference = context.getSharedPreferences(FORMATTER_TIME_FILE, Context.MODE_PRIVATE)

    override fun getFormatterRes(): Int {
        return preference.getInt(FORMATTER_TIME_PREFERENCE_KEY, 0)
    }

    override fun setFormatterRes(formatterId: Int) {
        preference.edit().putInt(FORMATTER_TIME_PREFERENCE_KEY, formatterId).apply()
    }

    companion object {
        const val FORMATTER_TIME_PREFERENCE_KEY = "FORMATTER_TIME_PREFERENCE_KEY"
        const val FORMATTER_TIME_FILE = "FORMATTER_TIME_FILE"
    }
}
