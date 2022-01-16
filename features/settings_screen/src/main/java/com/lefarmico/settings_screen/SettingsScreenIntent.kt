package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseIntent

sealed class SettingsScreenIntent : BaseIntent {

    object SetFullDateFormatter : SettingsScreenIntent()

    object GetFullDateFormatter : SettingsScreenIntent()

    object GetMonthDateFormatter : SettingsScreenIntent()

    object SetMonthDateFormatter : SettingsScreenIntent()

    object SetRemindTime : SettingsScreenIntent()

    object GetRemindTime : SettingsScreenIntent()
}
