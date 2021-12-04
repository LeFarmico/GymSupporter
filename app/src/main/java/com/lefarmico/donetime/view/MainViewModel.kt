package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseIntent
import com.lefarmico.core.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<BaseIntent>() {
    override fun onTriggerEvent(eventType: BaseIntent) {}
}
