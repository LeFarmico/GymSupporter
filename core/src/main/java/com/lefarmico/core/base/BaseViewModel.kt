package com.lefarmico.core.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T : BaseIntent> : ViewModel() {

    private val compositeDisposable get() = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    abstract fun onTriggerEvent(eventType: T)
}
