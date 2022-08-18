package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseState

sealed class MainState : BaseState.State {
    data class ThemeResult(val themeId: Int) : MainState()
}
