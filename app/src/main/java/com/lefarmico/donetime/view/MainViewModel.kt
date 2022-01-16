package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainIntent, MainState, MainEvent>() {
    override fun triggerIntent(intent: MainIntent) {}
}
