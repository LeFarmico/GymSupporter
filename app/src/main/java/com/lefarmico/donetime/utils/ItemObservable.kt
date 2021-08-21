package com.lefarmico.donetime.utils

interface ItemObservable<T> {
    val listObservers: MutableList<ItemObserver<T>>
    fun registerObserver(observer: ItemObserver<T>)
    fun removeObserver(observer: ItemObserver<T>)
    fun notifyObservers()
}
