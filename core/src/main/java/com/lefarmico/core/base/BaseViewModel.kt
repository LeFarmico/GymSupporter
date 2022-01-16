package com.lefarmico.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.core.utils.SingleLiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<I : BaseIntent, S : BaseState.State, E : BaseState.Event> :
    ViewModel(),
    IViewModelDispatcher<I, S, E> {

    private val compositeDisposable get() = CompositeDisposable()
    protected val mState = MutableLiveData<S>()
    protected val mEvent = SingleLiveEvent<E>()
    override val state: LiveData<S> get() = mState
    override val event: LiveData<E> get() = mEvent

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    final override fun dispatchIntent(intent: I) {
        triggerIntent(intent)
    }

    abstract fun triggerIntent(intent: I)
}
