package com.lefarmico.core.base

interface IViewStateReceiver<S : BaseState.State, E : BaseState.Event> {
    fun receive(state: S)
    fun receive(event: E)
}
