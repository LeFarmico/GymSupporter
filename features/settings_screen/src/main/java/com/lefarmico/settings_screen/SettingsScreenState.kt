package com.lefarmico.settings_screen

import com.lefarmico.core.base.BaseState

sealed class SettingsScreenState : BaseState.State {

    data class CurrentFullDateFormatterResult(val date: String) : SettingsScreenState()
}
