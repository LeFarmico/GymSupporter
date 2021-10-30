package com.lefarmico.core.selector

abstract class SelectItemsHandler<T>(private val callback: Callback<T>) {

    abstract fun selectedItemAction(item: T)

    fun onEachSelectedItemsAction() {
        callback.getSelectedItems().forEach {
            selectedItemAction(it)
        }
    }

    interface Callback<out T> {
        fun getSelectedItems(): Set<T>
    }
}
