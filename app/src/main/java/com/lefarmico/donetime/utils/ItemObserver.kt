package com.lefarmico.donetime.utils

interface ItemObserver<T> {
    fun updateData(items: MutableList<T>)
}
