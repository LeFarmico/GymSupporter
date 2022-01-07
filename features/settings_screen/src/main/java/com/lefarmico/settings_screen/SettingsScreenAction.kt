package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseAction

sealed class SettingsScreenAction : BaseAction {

    object SetFullDateFormatter : SettingsScreenAction()

    object GetCurrentDateFormatter : SettingsScreenAction()
}
