package com.lefarmico.presentation.viewModels

import com.lefarmico.presentation.views.base.BaseIntent
import com.lefarmico.presentation.views.base.BaseViewModel

class MainViewModel : BaseViewModel<BaseIntent>() {
    override fun onTriggerEvent(eventType: BaseIntent) {}
}
