package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.domain.preferences.FormatterMonthPreferenceHelper
import javax.inject.Inject

class FormatterMonthPreferenceHelperImpl @Inject constructor(
    context: Context
) : FormatterMonthPreferenceHelper {

    private val preference = context.getSharedPreferences(FORMATTER_MONTH_FILE, Context.MODE_PRIVATE)

    override fun getFormatterRes(): Int {
        return preference.getInt(FORMATTER_MONTH_PREFERENCE_KEY, 0)
    }

    override fun setFormatterRes(formatterId: Int) {
        preference.edit().putInt(FORMATTER_MONTH_PREFERENCE_KEY, formatterId).apply()
    }

    companion object {
        const val FORMATTER_MONTH_PREFERENCE_KEY = "FORMATTER_MONTH_PREFERENCE_KEY"
        const val FORMATTER_MONTH_FILE = "FORMATTER_MONTH_FILE"
    }
}
