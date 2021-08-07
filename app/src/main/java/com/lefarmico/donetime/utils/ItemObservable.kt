package com.lefarmico.donetime.utils

interface ItemObservable {
    val listObservers: MutableList<ItemObserver>
    fun registerObserver(observer: ItemObserver)
    fun removeObserver(observer: ItemObserver)
    fun notifyObservers()
}
