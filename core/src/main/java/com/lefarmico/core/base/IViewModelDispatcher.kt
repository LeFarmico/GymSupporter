package com.lefarmico.core.base

import androidx.lifecycle.LiveData

interface IViewModelDispatcher<
    I : BaseIntent, S : BaseState.State, E : BaseState.Event
    > {

    val state: LiveData<S>
    val event: LiveData<E>

    fun dispatchIntent(intent: I)
}
