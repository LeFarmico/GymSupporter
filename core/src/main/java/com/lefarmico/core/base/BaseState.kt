package com.lefarmico.core.base

sealed interface BaseState {
    interface State : BaseState
    interface Event : BaseState
}
