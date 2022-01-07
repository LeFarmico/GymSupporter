package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainIntent, MainAction, MainState, MainEvent>() {
    override fun triggerAction(action: MainAction) {}

    override fun intentToAction(intent: MainIntent): MainAction = MainAction()
}
