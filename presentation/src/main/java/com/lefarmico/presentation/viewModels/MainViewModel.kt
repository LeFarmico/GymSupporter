package com.lefarmico.presentation.viewModels

import com.lefarmico.presentation.views.base.BaseIntent
import com.lefarmico.presentation.views.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<BaseIntent>() {
    override fun onTriggerEvent(eventType: BaseIntent) {}
}
