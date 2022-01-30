package com.lefarmico.donetime.view

import com.lefarmico.core.base.BaseState

sealed class MainEvent : BaseState.Event {
    sealed class LoadDataResult : MainEvent() {
        object Success : LoadDataResult()
        object Failure : LoadDataResult()
    }
}
