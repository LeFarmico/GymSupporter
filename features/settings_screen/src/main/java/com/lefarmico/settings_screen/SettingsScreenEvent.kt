package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseState

sealed class SettingsScreenEvent : BaseState.Event {
    data class ChangeTheme(val themeId: Int) : SettingsScreenEvent()
    data class SetThemeMenuItem(val themeId: Int) : SettingsScreenEvent()
}
