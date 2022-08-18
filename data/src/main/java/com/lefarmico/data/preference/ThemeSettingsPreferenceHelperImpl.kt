package com.lefarmico.data.preference

import android.content.Context
import com.lefarmico.data.R
import com.lefarmico.domain.preferences.ThemeSettingsPreferenceHelper
import javax.inject.Inject

class ThemeSettingsPreferenceHelperImpl @Inject constructor(
    context: Context
) : ThemeSettingsPreferenceHelper {

    private val preference = context.getSharedPreferences(THEME_PREFERENCE_FILE, Context.MODE_PRIVATE)

    override fun getCurrentThemeRes(): Int {
        return preference.getInt(THEME_PREFERENCE_KEY, 0)
    }

    override fun setCurrentThemeRes(themeId: Int) {
        preference.edit().putInt(THEME_PREFERENCE_KEY, themeId).apply()
    }

    companion object {
        const val THEME_PREFERENCE_KEY = "THEME_PREFERENCE_KEY"
        const val THEME_PREFERENCE_FILE = "THEME_PREFERENCE_FILE"
    }
}
