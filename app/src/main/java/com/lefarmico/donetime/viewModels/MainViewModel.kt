package com.lefarmico.donetime.viewModels

import com.lefarmico.donetime.views.base.BaseIntent
import com.lefarmico.donetime.views.base.BaseViewModel

class MainViewModel : BaseViewModel<BaseIntent>() {
    override fun onTriggerEvent(eventType: BaseIntent) {}
}
