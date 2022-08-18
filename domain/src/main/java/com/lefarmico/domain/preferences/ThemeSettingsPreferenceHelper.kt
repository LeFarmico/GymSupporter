package com.lefarmico.domain.preferences

interface ThemeSettingsPreferenceHelper {

    fun getCurrentThemeRes(): Int

    fun setCurrentThemeRes(themeId: Int)
}
